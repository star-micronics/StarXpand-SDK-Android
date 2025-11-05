package com.starmicronics.starxpandsdk

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import com.starmicronics.stario10.FirmwareUpdateDelegate
import com.starmicronics.stario10.FirmwareUpdateStep
import com.starmicronics.stario10.InterfaceType
import com.starmicronics.stario10.StarConnectionSettings
import com.starmicronics.stario10.StarIO10DiagInfoUpload
import com.starmicronics.stario10.StarIO10Exception
import com.starmicronics.stario10.StarPrinter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FirmwareUpdateActivity : AppCompatActivity() {

    private val requestCode = 1000
    private lateinit var progressBar: ProgressBar
    private lateinit var progressTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firmware_update)

        val linerLayout = findViewById<LinearLayout>(R.id.linearLayout)
        Util.setPadding(linerLayout)

        this.progressBar = findViewById(R.id.progressBar)
        this.progressTextView = findViewById(R.id.textViewFirmwareUpdateStep)

        val checkVersionsButton = findViewById<Button>(R.id.buttonCheckVersions)
        checkVersionsButton.setOnClickListener { onPressCheckVersionsButton() }

        val updateButton = findViewById<Button>(R.id.buttonUpdate)
        updateButton.setOnClickListener { onPressUpdateButton() }

        // If you are using Android 12 and targetSdkVersion is 31 or later,
        // you have to request Bluetooth permission (Nearby devices permission) to use the Bluetooth printer.
        // https://developer.android.com/about/versions/12/features/bluetooth-permissions
        requestBluetoothPermission()

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (progressBar.isGone) {
                    finish()
                }
            }
        }
        onBackPressedDispatcher.addCallback(callback)
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
                Log.d("FirmwareUpdate", "PERMISSION ERROR: You have to allow Nearby devices to use the Bluetooth printer.")
                return null
            }
        }
        return StarPrinter(settings, applicationContext)
    }

    private fun lockScreen(lock: Boolean) {
        if (lock) {
            this.progressBar.visibility = View.VISIBLE
            this.progressTextView.visibility = View.VISIBLE
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            )
        } else {
            this.progressBar.visibility = View.GONE
            this.progressTextView.visibility = View.GONE
            this.progressTextView.text = ""
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
            this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        }
    }

    private fun onPressCheckVersionsButton() {
        val textView = findViewById<TextView>(R.id.firmwareUpdateTextViewIdentifier)
        textView.text = ""

        val printer = getStarPrinter() ?: return

        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            try {
                printer.openAsync().await()

                printer.setting?.firmware?.let { firmware ->

                    withContext(Dispatchers.Main) {
                        textView.append("isUpdatable : ${firmware.isUpdatable}\n")
                        textView.append("\nBefore checkVersionsAsync()\n")
                        textView.append("currentVersion : ${firmware.currentVersion}\n")
                        textView.append("latestVersion : ${firmware.latestVersion}\n")
                    }

                    firmware.checkVersionsAsync().await()

                    withContext(Dispatchers.Main) {
                        textView.append("\nAfter checkVersionsAsync()\n")
                        textView.append("currentVersion : ${firmware.currentVersion}\n")
                        textView.append("latestVersion : ${firmware.latestVersion}\n")
                    }

                    Log.d("FirmwareUpdate", "checkVersionsAsync() : Success")

                    withContext(Dispatchers.Main) {
                        textView.append("checkVersionsAsync() Success\n")
                    }
                } ?: run {
                    withContext(Dispatchers.Main) {
                        textView.append("Not Supported Model.\n")
                    }
                }
            } catch (e: Exception) {
                Log.d("FirmwareUpdate", "Error: $e")

                withContext(Dispatchers.Main) {
                    textView.append("Error: ${e}\n\n")
                }
            } finally {
                printer.closeAsync().await()
            }
        }
    }

    private fun onPressUpdateButton() {
        val textView = findViewById<TextView>(R.id.firmwareUpdateTextViewIdentifier)
        textView.text = ""

        val printer = getStarPrinter() ?: return

        val job = SupervisorJob()
        val scope = CoroutineScope(Dispatchers.Default + job)

        scope.launch {
            try {
                withContext(Dispatchers.Main) {
                    lockScreen(true)
                }

                printer.openAsync().await()

                printer.setting?.firmware?.let { firmware ->

                    firmware.updateDelegate = object : FirmwareUpdateDelegate() {
                        override fun onFirmwareUpdateProgress(step: FirmwareUpdateStep) {
                            Log.d("FirmwareUpdate update()", "onFirmwareUpdateProgress : $step")
                            scope.launch(Dispatchers.Main) {
                                textView.append("onFirmwareUpdateProgress : $step\n")
                                progressTextView.text = "$step"
                            }
                        }

                        override fun onFirmwareUpdateTransmitComplete() {
                            Log.d("FirmwareUpdate update()\"", "onFirmwareUpdateTransmitComplete")
                            scope.launch(Dispatchers.Main) {
                                textView.append("onFirmwareUpdateTransmitComplete\n")
                            }
                        }

                        override fun onFirmwareUpdateError(e: StarIO10Exception) {
                            Log.d("FirmwareUpdate update()\"", "onFirmwareUpdateError")
                            e.printStackTrace()
                            scope.launch(Dispatchers.Main) {
                                textView.append("onFirmwareUpdateError\n")
                            }
                        }
                    }

                    withContext(Dispatchers.Main) {
                        textView.append(getString(R.string.firmware_update_warning_writing))
                    }

                    // To disable uploading the information of user device and printer to the Star Micronics server by the .updateAsync() method,
                    // set the below property to false.
                    // StarIO10DiagInfoUpload.getInstance().isEnabled = false

                    firmware.updateAsync().await()

                    withContext(Dispatchers.Main) {
                        textView.append(getString(R.string.firmware_update_warning_Updating))
                    }

                    Log.d("FirmwareUpdate", "update() : Success, wait for printer reboot.")
                } ?: run {
                    withContext(Dispatchers.Main) {
                        textView.append("Not Supported Model.\n")
                    }
                }
            } catch (e: Exception) {
                Log.d("FirmwareUpdate", "Error: $e")

                withContext(Dispatchers.Main) {
                    textView.append("Error: ${e}\n\n")
                }
            } finally {
                printer.closeAsync().await()

                withContext(Dispatchers.Main) {
                    lockScreen(false)
                }
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