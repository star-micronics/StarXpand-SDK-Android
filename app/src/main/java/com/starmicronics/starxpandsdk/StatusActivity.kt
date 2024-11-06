package com.starmicronics.starxpandsdk

import android.Manifest
import android.content.pm.PackageManager
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

class StatusActivity : AppCompatActivity() {

    private val requestCode = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_status)

        val linerLayout = findViewById<LinearLayout>(R.id.linearLayout)
        Util.setPadding(linerLayout)

        val statusButton = findViewById<Button>(R.id.buttonStatus)
        statusButton.setOnClickListener { onPressGetStatusButton() }

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        requestBluetoothPermission()
    }

    private fun onPressGetStatusButton() {
        val editTextIdentifier = findViewById<EditText>(R.id.editTextIdentifier)
        val identifier = editTextIdentifier.text.toString()
        val statusTextView = findViewById<TextView>(R.id.statusTextViewIdentifier)

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
                Log.d("Status", "PERMISSION ERROR: You have to allow Nearby devices to use the Bluetooth printer.")
                return
            }
        }

        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            try {
                printer.openAsync().await()
                val status = printer.getStatusAsync().await()

                Log.d("Status", "Has Error: ${status.hasError}")
                Log.d("Status", "Paper Empty: ${status.paperEmpty}")
                Log.d("Status", "Paper Near Empty: ${status.paperNearEmpty}")
                Log.d("Status", "Cover Open: ${status.coverOpen}")
                Log.d("Status", "Drawer Open Close Signal: ${status.drawerOpenCloseSignal}")

                withContext(Dispatchers.Main) {
                    statusTextView.append("Has Error: ${status.hasError}\n")
                    statusTextView.append("Paper Empty: ${status.paperEmpty}\n")
                    statusTextView.append("Paper Near Empty: ${status.paperNearEmpty}\n")
                    statusTextView.append("Cover Open: ${status.coverOpen}\n")
                    statusTextView.append("Drawer Open Close Signal: ${status.drawerOpenCloseSignal}\n")
                    statusTextView.append("\n")
                }
            } catch (e: Exception) {
                Log.d("Status", "Error: ${e}")

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
