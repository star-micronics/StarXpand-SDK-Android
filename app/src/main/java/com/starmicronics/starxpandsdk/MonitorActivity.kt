//
// This sample code performs the minimum processing to communicate with the printer.
// In the actual application code, please implement it considering the exclusion control.
//

package com.starmicronics.starxpandsdk

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.starmicronics.stario10.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MonitorActivity : AppCompatActivity() {

    private var printer: StarPrinter? = null

    private var isMonitoring: Boolean = false

    private val requestCode = 1000

    private var buttonMonitor: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_monitor)

        buttonMonitor = findViewById<Button>(R.id.buttonMonitor)
        buttonMonitor?.setOnClickListener { onPressMonitorButton() }

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        requestBluetoothPermission()
    }

    override fun onStop() {
        super.onStop()

        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            printer?.closeAsync()?.await()
        }
    }

    private fun onPressMonitorButton() {
        if (isMonitoring) {
            stopMonitoring()
        } else {
            startMonitoring()
        }
    }

    private fun startMonitoring() {
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

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        if (interfaceType == InterfaceType.Bluetooth || settings.autoSwitchInterface) {
            if (!hasBluetoothPermission()) {
                Log.d("Monitor", "PERMISSION ERROR: You have to allow Nearby devices to use the Bluetooth printer.")
                return
            }
        }

        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            printer = StarPrinter(settings, applicationContext)
            printer?.printerDelegate = object : PrinterDelegate() {
                override fun onCommunicationError(e: StarIO10Exception) {
                    super.onCommunicationError(e)
                    Log.d("Monitor", "Printer: Communication Error")
                    Log.d("Monitor", "${e}")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Printer: Communication Error\n")
                        statusTextView.append("${e}\n")
                    }
                }

                override fun onReady() {
                    super.onReady()
                    Log.d("Monitor", "Printer: Ready")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Printer: Ready\n")
                    }
                }

                override fun onError() {
                    super.onError()
                    Log.d("Monitor", "Printer: Error")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Printer: Error\n")
                    }
                }

                override fun onPaperReady() {
                    super.onPaperReady()
                    Log.d("Monitor", "Printer: Paper Ready")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Printer: Paper Ready\n")
                    }
                }

                override fun onPaperNearEmpty() {
                    super.onPaperNearEmpty()
                    Log.d("Monitor", "Printer: Paper Near Empty")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Printer: Paper Near Empty\n")
                    }
                }

                override fun onPaperEmpty() {
                    super.onPaperEmpty()
                    Log.d("Monitor", "Printer: Paper Empty")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Printer: Paper Empty\n")
                    }
                }

                override fun onCoverOpened() {
                    super.onCoverOpened()
                    Log.d("Monitor", "Printer: Cover Opened")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Printer: Cover Opened\n")
                    }
                }

                override fun onCoverClosed() {
                    super.onCoverClosed()
                    Log.d("Monitor", "Printer: Cover Closed")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Printer: Cover Closed\n")
                    }
                }
            }

            printer?.drawerDelegate = object : DrawerDelegate() {
                override fun onCommunicationError(e: StarIO10Exception) {
                    super.onCommunicationError(e)
                    Log.d("Monitor", "Drawer: Communication Error")
                    Log.d("Monitor", "${e}")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Drawer: Communication Error\n")
                        statusTextView.append("${e}\n")
                    }
                }

                override fun onOpenCloseSignalSwitched(openCloseSignal: Boolean) {
                    super.onOpenCloseSignalSwitched(openCloseSignal)
                    Log.d("Monitor", "Drawer: Open Close Signal Switched: ${openCloseSignal}")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Drawer: Open Close Signal Switched: ${openCloseSignal}\n")
                    }
                }
            }

            printer?.inputDeviceDelegate = object : InputDeviceDelegate() {
                override fun onCommunicationError(e: StarIO10Exception) {
                    super.onCommunicationError(e)
                    Log.d("Monitor", "Input Device: Communication Error")
                    Log.d("Monitor", "${e}")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Input Device: Communication Error\n")
                        statusTextView.append("${e}\n")
                    }
                }

                override fun onConnected() {
                    super.onConnected()
                    Log.d("Monitor", "Input Device: Connected")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Input Device: Connected\n")
                    }
                }

                override fun onDisconnected() {
                    super.onDisconnected()
                    Log.d("Monitor", "Input Device: Disconnected")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Input Device: Disconnected\n")
                    }
                }

                override fun onDataReceived(data: List<Byte>) {
                    super.onDataReceived(data)
                    Log.d("Monitor", "Input Device: DataReceived ${data}")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Input Device: DataReceived ${data}\n")
                    }
                }
            }

            printer?.displayDelegate = object : DisplayDelegate() {
                override fun onCommunicationError(e: StarIO10Exception) {
                    super.onCommunicationError(e)
                    Log.d("Monitor", "Display: Communication Error")
                    Log.d("Monitor", "${e}")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Display: Communication Error\n")
                        statusTextView.append("${e}\n")
                    }
                }

                override fun onConnected() {
                    super.onConnected()
                    Log.d("Monitor", "Display: Connected")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Display: Connected\n")
                    }
                }

                override fun onDisconnected() {
                    super.onDisconnected()
                    Log.d("Monitor", "Display: Disconnected")

                    scope.launch(Dispatchers.Main) {
                        statusTextView.append("Display: Disconnected\n")
                    }
                }
            }

            try {
                printer?.openAsync()?.await()

                scope.launch(Dispatchers.Main) {
                    buttonMonitor?.text = "Stop Monitoring"
                    isMonitoring = true
                }
            } catch (e: Exception) {
                Log.d("Monitor", "Error: ${e}")

                scope.launch(Dispatchers.Main) {
                    statusTextView.append("Error: ${e}\n")
                }
            }
        }
    }

    private fun stopMonitoring() {
        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            printer?.closeAsync()?.await()

            scope.launch(Dispatchers.Main) {
                buttonMonitor?.text = "Start Monitoring"
                isMonitoring = false
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
