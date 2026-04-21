package com.starmicronics.starxpandsdk

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.starmicronics.stario10.DisplayDelegate
import com.starmicronics.stario10.DrawerDelegate
import com.starmicronics.stario10.InputDeviceDelegate
import com.starmicronics.stario10.InterfaceType
import com.starmicronics.stario10.PrinterDelegate
import com.starmicronics.stario10.StarConnectionSettings
import com.starmicronics.stario10.StarIO10Exception
import com.starmicronics.stario10.StarPrinter
import com.starmicronics.stario10.StarPrinterStatusDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun MonitorView(
    paddingValues: PaddingValues = WindowInsets.systemBars.asPaddingValues(),
    viewModel: MonitorViewModel = viewModel(),
) {
    val context = LocalContext.current
    var interfaceType by rememberSaveable { mutableStateOf(DefaultConnectionSettings.interfaceType) }
    var identifier by rememberSaveable { mutableStateOf(DefaultConnectionSettings.identifier) }

    val monitoringState = viewModel.isMonitoring.collectAsState()
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

        Button(
            onClick = {
                if (!monitoringState.value) {
                    viewModel.start(
                        StarConnectionSettings(
                            interfaceType = interfaceType,
                            identifier = identifier,
                        ),
                        context,
                    )
                } else {
                    viewModel.stop(
                    )
                }
            },
            modifier =
                Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            Text(
                if (!monitoringState.value) {
                    stringResource(id = R.string.start_monitor)
                } else {
                    stringResource(id = R.string.stop_monitor)
                },
            )
        }

        Text(
            text = logText.value,
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .verticalScroll(rememberScrollState()),
        )
    }

    LifecycleEventEffect(Lifecycle.Event.ON_STOP) {
        if (monitoringState.value) {
            viewModel.stop()
        }
    }
}

class MonitorViewModel: ViewModel() {
    private val _logText = MutableStateFlow("")
    val logText: StateFlow<String> get() = _logText

    private val _isMonitoring = MutableStateFlow(false)
    val isMonitoring: StateFlow<Boolean> get() = _isMonitoring

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> get() = _isProcessing

    private var printer: StarPrinter? = null

    fun start(
        starConnectionSettings: StarConnectionSettings,
        context: Context,
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            var log = ""
            _logText.value = log

            printer = StarPrinter(starConnectionSettings, context)
            try {
                printer?.printerDelegate = createPrinterDelegate(_logText)
                printer?.drawerDelegate = createDrawerDelegate(_logText)
                printer?.inputDeviceDelegate = createInputDeviceDelegate(_logText)
                printer?.displayDelegate = createDisplayDelegate(_logText)
                printer?.openAsync()?.await()

                _isMonitoring.value = true
                log = "Start Monitoring"
            } catch (e: Exception) {
                log = "Error: ${e.message}"
                printer?.closeAsync()?.await()
            }
            Log.d("Monitor", log)
            _logText.value = log + "\n"
            _isProcessing.value = false
        }
    }

    fun stop() {
        viewModelScope.launch {
            _isProcessing.value = true

            printer?.closeAsync()?.await()
            _isMonitoring.value = false

            val log = "Stop Monitoring"
            Log.d("Monitor", log)
            _logText.value += log

            _isProcessing.value = false
        }
    }

    private fun createPrinterDelegate(logText: MutableStateFlow<String>): PrinterDelegate {
        val delegate =
            object : PrinterDelegate() {
                override fun onCommunicationError(e: StarIO10Exception) {
                    super.onCommunicationError(e)
                    val log = "Printer: Communication Error\n${e.message}"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onReady() {
                    super.onReady()
                    val log = "Printer: Ready"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onError() {
                    super.onError()
                    val log = "Printer: Error"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onPaperReady() {
                    super.onPaperReady()
                    val log = "Printer: Paper Ready"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onPaperNearEmpty() {
                    super.onPaperNearEmpty()
                    val log = "Printer: Paper Near Empty"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onPaperEmpty() {
                    super.onPaperEmpty()
                    val log = "Printer: Paper Empty"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onCoverOpened() {
                    super.onCoverOpened()
                    val log = "Printer: Cover Opened"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onCoverClosed() {
                    super.onCoverClosed()
                    val log = "Printer: Cover Closed"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }
            }
        return delegate
    }

    private fun createDrawerDelegate(logText: MutableStateFlow<String>): DrawerDelegate {
        val delegate =
            object : DrawerDelegate() {
                override fun onCommunicationError(e: StarIO10Exception) {
                    super.onCommunicationError(e)
                    val log = "Drawer: Communication Error\n${e.message}"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onOpenCloseSignalSwitched(openCloseSignal: Boolean) {
                    super.onOpenCloseSignalSwitched(openCloseSignal)
                    val log = "Drawer: Open Close Signal Switched: $openCloseSignal"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onStatusChanged(statusDetail: StarPrinterStatusDetail) {
                    super.onStatusChanged(statusDetail)
                    val log = "Drawer: Status Details Changed"

//                    log += "\n"
//                    log += "    drawer1OpenedMethod: " + statusDetail.drawer1OpenedMethod + "\n"
//                    log += "    drawer2OpenedMethod: " + statusDetail.drawer2OpenedMethod + "\n"
//                    log += "    drawer3OpenedMethod: " + statusDetail.drawer3OpenedMethod + "\n"
//                    log += "    drawer4OpenedMethod: " + statusDetail.drawer4OpenedMethod + "\n"
//                    log += "    drawer1OpenCloseSignal: " + statusDetail.drawer1OpenCloseSignal + "\n"
//                    log += "    drawer2OpenCloseSignal: " + statusDetail.drawer2OpenCloseSignal + "\n"
//                    log += "    drawer3OpenCloseSignal: " + statusDetail.drawer3OpenCloseSignal + "\n"
//                    log += "    drawer4OpenCloseSignal: " + statusDetail.drawer4OpenCloseSignal + "\n"
//                    log += "    externalDevice1Connected: " + statusDetail.externalDevice1Connected + "\n"
//                    log += "    externalDevice2Connected: " + statusDetail.externalDevice2Connected + "\n"
//                    log += "    externalDevice3Connected: " + statusDetail.externalDevice3Connected + "\n"
//                    log += "    externalDevice4Connected: " + statusDetail.externalDevice4Connected + "\n"

                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }
            }
        return delegate
    }

    private fun createInputDeviceDelegate(logText: MutableStateFlow<String>): InputDeviceDelegate {
        val delegate =
            object : InputDeviceDelegate() {
                override fun onCommunicationError(e: StarIO10Exception) {
                    super.onCommunicationError(e)
                    val log = "Input Device: Communication Error\n${e.message}"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onConnected() {
                    super.onConnected()
                    val log = "Input Device: Connected"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onDisconnected() {
                    super.onDisconnected()
                    val log = "Input Device: Disconnected"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onDataReceived(data: List<Byte>) {
                    super.onDataReceived(data)
                    val log = "Input Device: Data Received: $data"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }
            }
        return delegate
    }

    private fun createDisplayDelegate(logText: MutableStateFlow<String>): DisplayDelegate {
        val delegate =
            object : DisplayDelegate() {
                override fun onCommunicationError(e: StarIO10Exception) {
                    super.onCommunicationError(e)
                    val log = "Display: Communication Error\n${e.message}"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onConnected() {
                    super.onConnected()
                    val log = "Display: Connected"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }

                override fun onDisconnected() {
                    super.onDisconnected()
                    val log = "Display: Disconnected"
                    Log.d("Monitor", log)
                    logText.value += log + "\n"
                }
            }
        return delegate
    }
}
