package com.starmicronics.starxpandsdk

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.starmicronics.stario10.*

class DiscoveryActivity: AppCompatActivity() {
    private var lanIsEnabled = true

    private var bluetoothIsEnabled = true

    private var usbIsEnabled = true

    private var _manager: StarDeviceDiscoveryManager? = null

    private val requestCode = 1000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discovery)
        
        val linerLayout = findViewById<LinearLayout>(R.id.linearLayout)
        Util.setPadding(linerLayout)

        val checkBoxLan = findViewById<CheckBox>(R.id.checkBoxLan)
        checkBoxLan.setOnClickListener { lanIsEnabled = checkBoxLan.isChecked }

        val checkBoxBluetooth = findViewById<CheckBox>(R.id.checkBoxBluetooth)
        checkBoxBluetooth.setOnClickListener { bluetoothIsEnabled = checkBoxBluetooth.isChecked }

        val checkBoxUsb = findViewById<CheckBox>(R.id.checkBoxUsb)
        checkBoxUsb.setOnClickListener { usbIsEnabled = checkBoxUsb.isChecked }

        val buttonDiscovery = findViewById<Button>(R.id.buttonDiscovery)
        buttonDiscovery.setOnClickListener { onPressDiscoveryButton() }

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        requestBluetoothPermission()
    }

    private fun onPressDiscoveryButton() {
        val editTextDevices = findViewById<EditText>(R.id.editTextDevices)

        editTextDevices.setText("")

        val interfaceTypes = mutableListOf<InterfaceType>()
        if (this.lanIsEnabled) {
            interfaceTypes += InterfaceType.Lan
        }
        if (this.bluetoothIsEnabled) {
            interfaceTypes += InterfaceType.Bluetooth
        }
        if (this.usbIsEnabled) {
            interfaceTypes += InterfaceType.Usb
        }

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        if (interfaceTypes.contains(InterfaceType.Bluetooth)) {
            if (!hasBluetoothPermission()) {
                Log.d("Discovery", "PERMISSION ERROR: You have to allow Nearby devices to use the Bluetooth printer.")
                return
            }
        }

        try {
            this._manager?.stopDiscovery()

            _manager = StarDeviceDiscoveryManagerFactory.create(
                interfaceTypes,
                applicationContext
            )
            _manager?.discoveryTime = 10000
            _manager?.callback = object : StarDeviceDiscoveryManager.Callback {
                override fun onPrinterFound(printer: StarPrinter) {
                    editTextDevices.append("${printer.connectionSettings.interfaceType}:${printer.connectionSettings.identifier}\n")

                    Log.d("Discovery", "Found printer: ${printer.connectionSettings.identifier}.")
                }

                override fun onDiscoveryFinished() {
                    Log.d("Discovery", "Discovery finished.")
                }
            }

            _manager?.startDiscovery()
        } catch (e: Exception) {
            Log.d("Discovery", "Error: ${e}")
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
