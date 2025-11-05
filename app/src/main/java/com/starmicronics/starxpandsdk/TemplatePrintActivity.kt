package com.starmicronics.starxpandsdk

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import java.util.Scanner
import com.starmicronics.stario10.*
import com.starmicronics.stario10.starxpandcommand.*
import com.starmicronics.stario10.starxpandcommand.printer.*

class TemplatePrintActivity : AppCompatActivity() {

    private val requestCode = 1000

    private var templateSpinner: Spinner? = null

    private var fieldDataSpinner: Spinner? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_template_print)

        val linerLayout = findViewById<LinearLayout>(R.id.linearLayout)
        Util.setPadding(linerLayout)

        val button = findViewById<Button>(R.id.buttonPrinting)
        button.setOnClickListener { onPressPrintButton() }

        templateSpinner = findViewById(R.id.spinnerTemplate)
        templateSpinner?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                fieldDataSpinner?.adapter = if (position == 0) {
                    ArrayAdapter.createFromResource(applicationContext, R.array.listReceiptWithSpecifyingNumberOfCharactersFieldData, android.R.layout.simple_spinner_dropdown_item)
                }
                else if (position == 1) {
                    ArrayAdapter.createFromResource(applicationContext, R.array.listReceiptWithoutSpecifyingNumberOfCharactersFieldData, android.R.layout.simple_spinner_dropdown_item)
                }
                else {
                    ArrayAdapter.createFromResource(applicationContext, R.array.listLabelFieldData, android.R.layout.simple_spinner_dropdown_item)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) { }
        }

        fieldDataSpinner = findViewById(R.id.spinnerFieldData)

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        requestBluetoothPermission()
    }

    private fun onPressPrintButton() {
        val editTextIdentifier = findViewById<EditText>(R.id.editTextIdentifier)
        val identifier = editTextIdentifier.text.toString()

        val spinnerInterfaceType = findViewById<Spinner>(R.id.spinnerInterfaceType)
        val interfaceType = when (spinnerInterfaceType.selectedItem.toString()) {
            "LAN" -> InterfaceType.Lan
            "Bluetooth" -> InterfaceType.Bluetooth
            "USB" -> InterfaceType.Usb
            else -> return
        }

        val settings = StarConnectionSettings(interfaceType, identifier)
        val printer = StarPrinter(settings, applicationContext)

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        if (interfaceType == InterfaceType.Bluetooth || settings.autoSwitchInterface) {
            if (!hasBluetoothPermission()) {
                Log.d("Printing", "PERMISSION ERROR: You have to allow Nearby devices to use the Bluetooth printer.")
                return
            }
        }

        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        val selectedTemplateId = templateSpinner?.selectedItemId ?: return
        val selectedFieldDataId = fieldDataSpinner?.selectedItemId ?: return

        scope.launch {
            try {
                val template = if (selectedTemplateId == 0L) {
                    createReceiptWithSpecifyingNumberOfCharactersTemplate()
                }
                else if (selectedTemplateId == 1L) {
                    createReceiptWithoutSpecifyingNumberOfCharactersTemplate()
                }
                else {
                    createLabelTemplate()
                }

                val inputStream = applicationContext.resources.openRawResource(
                    when {
                        selectedTemplateId == 0L && selectedFieldDataId == 0L -> R.raw.receipt1_field_data
                        selectedTemplateId == 0L && selectedFieldDataId == 1L -> R.raw.receipt2_field_data
                        selectedTemplateId == 1L && selectedFieldDataId == 0L -> R.raw.receipt1_field_data
                        selectedTemplateId == 1L && selectedFieldDataId == 1L -> R.raw.receipt3_field_data
                        selectedTemplateId == 2L && selectedFieldDataId == 0L -> R.raw.label1_field_data
                        else -> R.raw.label2_field_data
                    }
                )

                val fieldData: String = Scanner(inputStream).useDelimiter("\\A").next()

                printer.template = template

                printer.openAsync().await()
                printer.printAsync(fieldData).await()

                Log.d("Printing", "Success")
            } catch (e: Exception) {
                Log.d("Printing", "Error: ${e}")
            } finally {
                printer.closeAsync().await()
            }
        }
    }

    private fun createReceiptWithSpecifyingNumberOfCharactersTemplate(): String {
        val logo = BitmapFactory.decodeResource(resources, R.drawable.logo_01)
        val signature = BitmapFactory.decodeResource(resources, R.drawable.signature)

        val builder = StarXpandCommandBuilder()

        builder.addDocument(
            DocumentBuilder()
                .settingPrintableArea(48.0)
                .addPrinter(
                    PrinterBuilder()
                        .actionPrintImage(ImageParameter(logo, 406))
                        .styleInternationalCharacter(InternationalCharacterType.Usa)
                        .styleCharacterSpace(0.0)
                        .add(
                            PrinterBuilder()
                                .styleAlignment(Alignment.Center)
                                .styleBold(true)
                                .styleInvert(true)
                                .styleMagnification(MagnificationParameter(2, 2))
                                .actionPrintText("\${store_name}\n")
                        )
                        .actionFeed(1.0)
                        .actionPrintText(
                            "Order \${order_number}",
                            TextParameter()
                                .setWidth(
                                    16
                                )
                        )
                        .actionPrintText(" ")
                        .actionPrintText(
                            "\${time}\n",
                            TextParameter()
                                .setWidth(
                                    15,
                                    TextWidthParameter()
                                        .setAlignment(TextAlignment.Right)
                                        .setEllipsizeType(TextEllipsizeType.End)
                                )
                        )
                        .actionPrintText(
                            "Sale for \${sales_type}",
                            TextParameter()
                                .setWidth(16)
                        )
                        .actionPrintText(" ")
                        .actionPrintText(
                            "Served by \${server}\n",
                            TextParameter()
                                .setWidth(
                                    15,
                                    TextWidthParameter()
                                        .setAlignment(TextAlignment.Right)
                                        .setEllipsizeType(TextEllipsizeType.End)
                                )
                        )
                        .actionPrintText(
                            "Transaction #\${transaction_id}\n"
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .add(
                            PrinterBuilder(
                                PrinterParameter()
                                    .setTemplateExtension(
                                        TemplateExtensionParameter()
                                            .setEnableArrayFieldData(true)
                                    )
                            )
                                .actionPrintText(
                                    "\${item_list.quantity}",
                                    TextParameter()
                                        .setWidth(2)
                                )
                                .actionPrintText(
                                    "\${item_list.name}",
                                    TextParameter()
                                        .setWidth(24)
                                )
                                .actionPrintText(
                                    "\${item_list.unit_price%6.2lf}\n",
                                    TextParameter()
                                        .setWidth(6)
                                )
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionPrintText(
                            "Subtotal",
                            TextParameter()
                                .setWidth(26)
                        )
                        .actionPrintText(
                            "\${subtotal%6.2lf}\n",
                            TextParameter()
                                .setWidth(
                                    6,
                                    TextWidthParameter()
                                        .setEllipsizeType(TextEllipsizeType.End)
                                )
                        )
                        .actionPrintText(
                            "Tax",
                            TextParameter()
                                .setWidth(26)
                        )
                        .actionPrintText(
                            "\${tax%6.2lf}\n",
                            TextParameter()
                                .setWidth(
                                    6,
                                    TextWidthParameter()
                                        .setEllipsizeType(TextEllipsizeType.End)
                                )
                        )
                        .add(
                            PrinterBuilder()
                                .styleBold(true)
                                .actionPrintText(
                                    "Total",
                                    TextParameter()
                                        .setWidth(26)
                                )
                                .actionPrintText(
                                    "\${total%6.2lf}\n",
                                    TextParameter()
                                        .setWidth(
                                            6,
                                            TextWidthParameter()
                                                .setEllipsizeType(TextEllipsizeType.End)
                                        )
                                )
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionPrintText(
                            "\${credit_card_number}",
                            TextParameter()
                                .setWidth(26)
                        )
                        .actionPrintText(
                            "\${total%6.2lf}\n",
                            TextParameter()
                                .setWidth(
                                    6,
                                    TextWidthParameter()
                                        .setEllipsizeType(TextEllipsizeType.End)
                                )
                        )
                        .actionPrintText(
                            "Approval Code",
                            TextParameter()
                                .setWidth(16)
                        )
                        .actionPrintText(
                            "\${approval_code}\n",
                            TextParameter()
                                .setWidth(
                                    16,
                                    TextWidthParameter()
                                        .setAlignment(TextAlignment.Right)
                                )
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionPrintText(
                            "Amount",
                            TextParameter()
                                .setWidth(26)
                        )
                        .actionPrintText(
                            "\${amount%6.2lf}\n",
                            TextParameter()
                                .setWidth(
                                    6,
                                    TextWidthParameter()
                                        .setEllipsizeType(TextEllipsizeType.End)
                                )
                        )
                        .actionPrintText(
                            "Total",
                            TextParameter()
                                .setWidth(26)
                        )
                        .actionPrintText(
                            "\${total%6.2lf}\n",
                            TextParameter()
                                .setWidth(
                                    6,
                                    TextWidthParameter()
                                        .setEllipsizeType(TextEllipsizeType.End)
                                )
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionPrintText(
                            "Signature\n"
                        )
                        .add(
                            PrinterBuilder()
                                .styleAlignment(Alignment.Center)
                                .actionPrintImage(
                                    ImageParameter(signature,  256)
                                )
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(32.0)
                                .setX(8.0)
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionPrintText("\n")
                        .styleAlignment(Alignment.Center)
                        .actionPrintText(
                            "\${address}\n"
                        )
                        .actionPrintText(
                            "\${tel}\n"
                        )
                        .actionPrintText(
                            "\${mail}\n"
                        )
                        .actionFeed(1.0)
                        .actionPrintText(
                            "\${url}\n"
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionFeed(2.0)
                        .actionPrintText(
                            "Powered by Star Micronics\n"
                        )
                        .actionPrintBarcode(
                            BarcodeParameter("\${transaction_id}", BarcodeSymbology.Code128)
                                .setPrintHri(true)
                        )
                        .actionCut(CutType.Partial)
                )
        )

        return builder.getCommands()
    }

    private fun createReceiptWithoutSpecifyingNumberOfCharactersTemplate(): String {
        val logo = BitmapFactory.decodeResource(resources, R.drawable.logo_01)
        val signature = BitmapFactory.decodeResource(resources, R.drawable.signature)

        val builder = StarXpandCommandBuilder()

        builder.addDocument(
            DocumentBuilder()
                .settingPrintableArea(48.0)
                .addPrinter(
                    PrinterBuilder()
                        .actionPrintImage(ImageParameter(logo, 406))
                        .styleInternationalCharacter(InternationalCharacterType.Usa)
                        .styleCharacterSpace(0.0)
                        .add(
                            PrinterBuilder()
                                .styleAlignment(Alignment.Center)
                                .styleBold(true)
                                .styleInvert(true)
                                .styleMagnification(MagnificationParameter(2, 2))
                                .actionPrintText("\${store_name}\n")
                        )
                        .actionFeed(1.0)
                        .styleHorizontalTabPositions(listOf(17))
                        .actionPrintText(
                            "Order \${order_number}\t\${time}\n"
                        )
                        .actionPrintText(
                            "Sale for \${sales_type}\tServed by \${server}\n"
                        )
                        .actionPrintText(
                            "Transaction #\${transaction_id}\n"
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .add(
                            PrinterBuilder(
                                PrinterParameter()
                                    .setTemplateExtension(
                                        TemplateExtensionParameter()
                                            .setEnableArrayFieldData(true)
                                    )
                            )
                                .styleHorizontalTabPositions(listOf(2, 26))
                                .actionPrintText(
                                    "\${item_list.quantity}\t\${item_list.name}\t\${item_list.unit_price%6.2lf}\n"
                                )
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .styleHorizontalTabPositions(listOf(26))
                        .actionPrintText(
                            "Subtotal\t\${subtotal%6.2lf}\n"
                        )
                        .actionPrintText(
                            "Tax\t\${tax%6.2lf}\n"
                        )
                        .add(
                            PrinterBuilder()
                                .styleBold(true)
                                .actionPrintText(
                                    "Total\t\${total%6.2lf}\n"
                                )
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionPrintText(
                            "\${credit_card_number}\t\${total%6.2lf}\n"
                        )
                        .actionPrintText(
                            "Approval Code\t\${approval_code}\n"
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionPrintText(
                            "Amount\t\${amount%6.2lf}\n"
                        )
                        .actionPrintText(
                            "Total\t\${total%6.2lf}\n"
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionPrintText(
                            "Signature\n"
                        )
                        .add(
                            PrinterBuilder()
                                .styleAlignment(Alignment.Center)
                                .actionPrintImage(
                                    ImageParameter(signature,  256)
                                )
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(32.0)
                                .setX(8.0)
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionPrintText("\n")
                        .styleAlignment(Alignment.Center)
                        .actionPrintText(
                            "\${address}\n"
                        )
                        .actionPrintText(
                            "\${tel}\n"
                        )
                        .actionPrintText(
                            "\${mail}\n"
                        )
                        .actionFeed(1.0)
                        .actionPrintText(
                            "\${url}\n"
                        )
                        .actionPrintRuledLine(
                            RuledLineParameter(48.0)
                        )
                        .actionFeed(2.0)
                        .actionPrintText(
                            "Powered by Star Micronics\n"
                        )
                        .actionPrintBarcode(
                            BarcodeParameter("\${transaction_id}", BarcodeSymbology.Code128)
                                .setPrintHri(true)
                        )
                        .actionCut(CutType.Partial)
                )
        )

        return builder.getCommands()
    }

    private fun createLabelTemplate(): String {
        val builder = StarXpandCommandBuilder()

        builder.addDocument(
            DocumentBuilder()
                .addPrinter(
                    PrinterBuilder()
                        .addPageMode(
                            PageModeAreaParameter(48.0, 30.0),
                            PageModeBuilder()
                                .styleHorizontalPositionTo(4.0)
                                .actionPrintText("\${item_name}\n")
                                .styleVerticalPositionTo(6.0)
                                .actionPrintBarcode(
                                    BarcodeParameter("\${sku}", BarcodeSymbology.Ean13)
                                        .setHeight(5.0)
                                        .setPrintHri(true)
                                )
                                .add(
                                    PageModeBuilder()
                                        .styleFont(FontType.B)
                                        .styleVerticalPositionTo(0.0)
                                        .styleHorizontalPositionTo(34.0)
                                        .actionPrintText("EUR")
                                        .styleVerticalPositionTo(0.0)
                                        .styleHorizontalPositionTo(43.0)
                                        .actionPrintText("UK")
                                )
                                .add(
                                    PageModeBuilder()
                                        .styleMagnification(MagnificationParameter(2, 2))
                                        .stylePrintDirection(PageModePrintDirection.BottomToTop)
                                        .styleVerticalPositionTo(35.0)
                                        .styleHorizontalPositionTo(5.0)
                                        .actionPrintText("\${price_eur%.2lf}")
                                        .styleVerticalPositionTo(43.0)
                                        .styleHorizontalPositionTo(5.0)
                                        .actionPrintText("\${price_gbp%.2lf}")
                                )
                                .addPageMode(
                                    PageModeAreaParameter(32.0, 15.0)
                                        .setY(15.0),
                                    PageModeBuilder()
                                        .actionPrintText("\${maker_information}")
                                )
                        )
                        .actionCut(CutType.Partial)
                )
        )

        return builder.getCommands()
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