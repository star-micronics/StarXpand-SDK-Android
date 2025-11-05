package com.starmicronics.starxpandsdk

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import kotlinx.coroutines.*
import com.starmicronics.stario10.*
import com.starmicronics.stario10.starxpandcommand.*
import com.starmicronics.stario10.starxpandcommand.printer.*

class SpoolerActivity : AppCompatActivity() {

    private val requestCode = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spooler)

        val linerLayout = findViewById<LinearLayout>(R.id.linearLayout)
        Util.setPadding(linerLayout)

        val spoolPrintButton = findViewById<Button>(R.id.buttonSpoolPrint)
        spoolPrintButton.setOnClickListener { onPressSpoolPrintButton() }

        val getJobStatusButton = findViewById<Button>(R.id.buttonGetJobStatus)
        getJobStatusButton.setOnClickListener { onPressGetJobStatusButton() }

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        requestBluetoothPermission()
    }

    private fun getStarPrinter(): StarPrinter? {
        val editTextIdentifier = findViewById<EditText>(R.id.editTextIdentifier)
        val identifier = editTextIdentifier.text.toString()

        val spinnerInterfaceType = findViewById<Spinner>(R.id.spinnerInterfaceType)
        val interfaceType = when (spinnerInterfaceType.selectedItem.toString()) {
            "LAN" -> InterfaceType.Lan
            "Bluetooth" -> InterfaceType.Bluetooth
            "USB" -> InterfaceType.Usb
            else -> InterfaceType.Unknown
        }

        val settings = StarConnectionSettings(interfaceType, identifier)

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        if (interfaceType == InterfaceType.Bluetooth || settings.autoSwitchInterface) {
            if (!hasBluetoothPermission()) {
                Log.d("Spooler", "PERMISSION ERROR: You have to allow Nearby devices to use the Bluetooth printer.")
                return null
            }
        }
        return StarPrinter(settings, applicationContext)
    }

    private fun onPressSpoolPrintButton() {
        val statusTextView = findViewById<TextView>(R.id.statusTextViewIdentifier)
        val editTextJobId = findViewById<EditText>(R.id.editTextJobId)

        val printer = getStarPrinter() ?: return

        val logo = BitmapFactory.decodeResource(resources, R.drawable.logo_01)

        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            try {
                val builder = StarXpandCommandBuilder()
                builder.addDocument(
                    DocumentBuilder()
                        .addPrinter(
                            PrinterBuilder()
                                .actionPrintImage(ImageParameter(logo, 406))
                                .styleInternationalCharacter(InternationalCharacterType.Usa)
                                .styleCharacterSpace(0.0)
                                .styleAlignment(Alignment.Center)
                                .actionPrintText(
                                    "Star Clothing Boutique\n" +
                                            "123 Star Road\n" +
                                            "City, State 12345\n" +
                                            "\n"
                                )
                                .styleAlignment(Alignment.Left)
                                .actionPrintText(
                                    "Date:MM/DD/YYYY    Time:HH:MM PM\n" +
                                            "--------------------------------\n" +
                                            "\n"
                                )
                                .add(
                                    PrinterBuilder()
                                        .styleBold(true)
                                        .actionPrintText("SALE\n")
                                )
                                .actionPrintText(
                                    "SKU         Description    Total\n" +
                                            "300678566   PLAIN T-SHIRT  10.99\n" +
                                            "300692003   BLACK DENIM    29.99\n" +
                                            "300651148   BLUE DENIM     29.99\n" +
                                            "300642980   STRIPED DRESS  49.99\n" +
                                            "300638471   BLACK BOOTS    35.99\n" +
                                            "\n" +
                                            "Subtotal                  156.95\n" +
                                            "Tax                         0.00\n" +
                                            "--------------------------------\n"
                                )
                                .actionPrintText("Total     ")
                                .add(
                                    PrinterBuilder()
                                        .styleMagnification(MagnificationParameter(2, 2))
                                        .actionPrintText("   $156.95\n")
                                )
                                .actionPrintText(
                                    "--------------------------------\n" +
                                            "\n" +
                                            "Charge\n" +
                                            "156.95\n" +
                                            "Visa XXXX-XXXX-XXXX-0123\n" +
                                            "\n"
                                )
                                .add(
                                    PrinterBuilder()
                                        .styleInvert(true)
                                        .actionPrintText("Refunds and Exchanges\n")
                                )
                                .actionPrintText("Within ")
                                .add(
                                    PrinterBuilder()
                                        .styleUnderLine(true)
                                        .actionPrintText("30 days")
                                )
                                .actionPrintText(" with receipt\n")
                                .actionPrintText(
                                    "And tags attached\n" +
                                            "\n"
                                )
                                .styleAlignment(Alignment.Center)
                                .actionPrintBarcode(
                                    BarcodeParameter("0123456", BarcodeSymbology.Jan8)
                                        .setBarDots(3)
                                        .setHeight(5.0)
                                        .setPrintHri(true)
                                )
                                .actionFeedLine(1)
                                .actionPrintQRCode(
                                    QRCodeParameter("Hello, World\n")
                                        .setLevel(QRCodeLevel.L)
                                        .setCellSize(8)
                                )
                                .actionCut(CutType.Partial)
                        )
                )
                val commands = builder.getCommands()

                printer.openAsync().await()

                val jobSettings = StarSpoolJobSettings(true, 30, "Print from Android")

                val jobId = printer.printAsync(commands, jobSettings).await()
                Log.d("Spooler", "jobID : $jobId")

                withContext(Dispatchers.Main) {
                    editTextJobId.setText(jobId.toString())
                }

                Log.d("Spooler", "Success")
            } catch (e: Exception) {
                Log.d("Spooler", "Error: ${e}")

                withContext(Dispatchers.Main) {
                    statusTextView.append("Error: ${e}\n\n")
                }
            } finally {
                printer.closeAsync().await()
            }
        }
    }

    private fun onPressGetJobStatusButton() {
        val statusTextView = findViewById<TextView>(R.id.statusTextViewIdentifier)
        val editTextJobId = findViewById<EditText>(R.id.editTextJobId)

        val printer = getStarPrinter() ?: return

        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            try {
                printer.openAsync().await()

                val jobId = editTextJobId.text.toString().toInt()

                var jobStatus = printer.getSpoolJobStatusAsync(jobId).await()
                Log.d("Spooler", "jobStatus : $jobStatus")

                withContext(Dispatchers.Main) {
                    statusTextView.append("${jobStatus}\n")
                }

                Log.d("Spooler", "Success")
            } catch (e: Exception) {
                Log.d("Spooler", "Error: ${e}")

                withContext(Dispatchers.Main) {
                    statusTextView.append("Error: ${e}\n\n")
                }
            } finally {
                printer.closeAsync().await()
            }
        }
    }

    private fun requestBluetoothPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return
        }

        if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.BLUETOOTH_CONNECT,
                ), requestCode
            )
        }
    }

    private fun hasBluetoothPermission(): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            return true
        }

        return checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED
    }
}