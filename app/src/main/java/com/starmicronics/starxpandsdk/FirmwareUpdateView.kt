package com.starmicronics.starxpandsdk

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.starmicronics.stario10.FirmwareUpdateDelegate
import com.starmicronics.stario10.FirmwareUpdateStep
import com.starmicronics.stario10.InterfaceType
import com.starmicronics.stario10.StarConnectionSettings
import com.starmicronics.stario10.StarIO10Exception
import com.starmicronics.stario10.StarPrinter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun FirmwareUpdateView(
    paddingValues: PaddingValues = WindowInsets.systemBars.asPaddingValues(),
    viewModel: FirmwareUpdateViewModel = viewModel(),
) {
    val context = LocalContext.current
    var interfaceType by rememberSaveable { mutableStateOf(DefaultConnectionSettings.interfaceType) }
    var identifier by rememberSaveable { mutableStateOf(DefaultConnectionSettings.identifier) }

    val logText = viewModel.logText.collectAsState()

    Column(
        modifier =
            Modifier
                .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(id = R.string.interface_type),
            modifier =
                Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxWidth(),
        )
        SingleChoiceSegmentedButtonRow(
            modifier =
                Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            val interfaceTypes = listOf(InterfaceType.Lan, InterfaceType.Bluetooth, InterfaceType.BluetoothLE, InterfaceType.Usb)
            interfaceTypes.forEachIndexed { index, value ->
                SegmentedButton(
                    shape =
                        SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = interfaceTypes.size,
                        ),
                    onClick = { interfaceType = value },
                    selected = value == interfaceType,
                    label = { Text(value.toString()) },
                )
            }
        }

        TextField(
            value = identifier,
            onValueChange = { identifier = it },
            label = { Text(stringResource(id = R.string.identifier)) },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            Button(
                onClick = {
                    viewModel.checkVersions(
                        StarConnectionSettings(
                            interfaceType = interfaceType,
                            identifier = identifier,
                        ),
                        context,
                    )
                },
            ) {
                Text(stringResource(id = R.string.check_versions))
            }

            Button(
                onClick = {
                    viewModel.update(
                        StarConnectionSettings(
                            interfaceType = interfaceType,
                            identifier = identifier,
                        ),
                        context,
                    )
                },
            ) {
                Text(stringResource(id = R.string.update))
            }
        }

        Text(
            text = logText.value,
            modifier = Modifier.padding(8.dp),
        )
    }
}

class FirmwareUpdateViewModel: ViewModel() {
    private val _logText = MutableStateFlow("")
    val logText: StateFlow<String> get() = _logText

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> get() = _isProcessing

    fun checkVersions(
        starConnectionSettings: StarConnectionSettings,
        context: Context,
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            var log = ""
            _logText.value = log

            val printer = StarPrinter(starConnectionSettings, context)
            try {
                printer.openAsync().await()

                printer.setting?.firmware?.let { firmware ->
                    _logText.value += "isUpdateAvailable: ${firmware.isUpdatable}\n\n"

                    _logText.value += "Before checkVersions()\n"
                    _logText.value += "currentVersion: ${firmware.currentVersion}\n"
                    _logText.value += "latestVersion: ${firmware.latestVersion}\n\n"

                    firmware.checkVersionsAsync().await()

                    _logText.value += "After checkVersions()\n"
                    _logText.value += "currentVersion: ${firmware.currentVersion}\n"
                    _logText.value += "latestVersion: ${firmware.latestVersion}\n"

                    log = "Success"
                } ?: run {
                    log = "Not Supported Model."
                }
            } catch (e: Exception) {
                log = "Error: ${e.message}"
            } finally {
                printer.closeAsync().await()
            }
            Log.d("FirmwareUpdate", log)
            _logText.value += log
            _isProcessing.value = false
        }
    }

    fun update(
        starConnectionSettings: StarConnectionSettings,
        context: Context,
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            var log = ""
            _logText.value = ""

            val printer = StarPrinter(starConnectionSettings, context)
            try {
                printer.openAsync().await()

                printer.setting?.firmware?.let { firmware ->
                    firmware.updateDelegate = createFirmwareUpdateDelegate(_logText)

                    // To disable uploading the information of user device and printer to the Star Micronics server by the .updateAsync() method,
                    // set the below property to false.
                    // StarIO10DiagInfoUpload.getInstance().isEnabled = false

                    _logText.value +=
                        "Caution : The firmware is being updated. Until the result is displayed, please leave the printer turned on and wait without operating the screen of this device.\n\n"

                    firmware.updateAsync().await()

                    _logText.value +=
                        "Firmware data transmission has been completed. The firmware is being updated.　This process may take several minutes.\n\n"

                    log = "Success"
                } ?: run {
                    log = "Not Supported Model."
                }
            } catch (e: Exception) {
                log = "Error: ${e.message}"
            } finally {
                printer.closeAsync().await()
            }
            Log.d("FirmwareUpdate", log)
            _logText.value += log
            _isProcessing.value = false
        }
    }

    private fun createFirmwareUpdateDelegate(logText: MutableStateFlow<String>): FirmwareUpdateDelegate {
        val delegate =
            object : FirmwareUpdateDelegate() {
                override fun onFirmwareUpdateProgress(step: FirmwareUpdateStep) {
                    val log = "Firmware update progress: $step"
                    Log.d("FirmwareUpdate", log)
                    logText.value += "$log\n"
                }

                override fun onFirmwareUpdateTransmitComplete() {
                    val log = "Firmware data transmission has been completed."
                    Log.d("FirmwareUpdate", log)
                    logText.value += log
                }

                override fun onFirmwareUpdateError(e: StarIO10Exception) {
                    val log = "Firmware update error: ${e.message}"
                    Log.d("FirmwareUpdate", log)
                    logText.value += log
                }
            }
        return delegate
    }
}
