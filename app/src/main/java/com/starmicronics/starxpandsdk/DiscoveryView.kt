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
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.starmicronics.stario10.InterfaceType
import com.starmicronics.stario10.StarDeviceDiscoveryManager
import com.starmicronics.stario10.StarDeviceDiscoveryManagerFactory
import com.starmicronics.stario10.StarPrinter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Preview(showBackground = true)
@Composable
fun DiscoveryView(
    paddingValues: PaddingValues = WindowInsets.systemBars.asPaddingValues(),
    viewModel: DiscoveryViewModel = viewModel(),
) {
    val context = LocalContext.current
    val interfaceTypes = listOf(InterfaceType.Lan, InterfaceType.Bluetooth, InterfaceType.BluetoothLE, InterfaceType.Usb)
    var selectedInterfaceTypes: Set<Any> by rememberSaveable { mutableStateOf(interfaceTypes.toSet()) }

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
        SingleChoiceSegmentedButtonRow(modifier = Modifier.padding(all = 8.dp)) {
            interfaceTypes.forEachIndexed { index, value ->
                val isSelected = selectedInterfaceTypes.contains(value)
                SegmentedButton(
                    shape =
                        SegmentedButtonDefaults.itemShape(
                            index = index,
                            count = interfaceTypes.size,
                        ),
                    onClick = {
                        selectedInterfaceTypes =
                            if (isSelected) {
                                selectedInterfaceTypes - value
                            } else {
                                selectedInterfaceTypes + value
                            }
                    },
                    selected = isSelected,
                    label = { Text(value.toString()) },
                )
            }
        }

        Button(
            onClick = {
                viewModel.discovery(
                    selectedInterfaceTypes.filterIsInstance<InterfaceType>(),
                    context,
                )
            },
        ) {
            Text(stringResource(id = R.string.discovery))
        }

        SelectionContainer {
            Text(
                text = logText.value,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp)
                        .verticalScroll(rememberScrollState()),
            )
        }
    }
}

class DiscoveryViewModel (): ViewModel() {
    private val _logText = MutableStateFlow("")
    val logText: StateFlow<String> get() = _logText

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> get() = _isProcessing

    private var manager: StarDeviceDiscoveryManager? = null

    fun discovery(
        interfaceTypes: List<InterfaceType>,
        context: Context,
    ) {
        _isProcessing.value = true
        var log = ""
        _logText.value = log

        try {
            manager?.stopDiscovery()

            manager =
                StarDeviceDiscoveryManagerFactory.create(
                    interfaceTypes,
                    context,
                )

            manager?.discoveryTime =
                if (interfaceTypes.contains(InterfaceType.BluetoothLE)) 10_000
                else if (interfaceTypes.contains(InterfaceType.Lan)) 5_000
                else 2_000
            manager?.callback = createDiscoveryManagerCallback()

            manager?.startDiscovery()

            log = "Discovery started for: ${interfaceTypes.joinToString(", ")}"
        } catch (e: Exception) {
            log = "Error: ${e.message}"
            _isProcessing.value = false
        }
        Log.d("Discovery", log)
    }

    private fun createDiscoveryManagerCallback(): StarDeviceDiscoveryManager.Callback =
        object : StarDeviceDiscoveryManager.Callback {
            override fun onPrinterFound(printer: StarPrinter) {
                val interfaceType = printer.connectionSettings.interfaceType
                val identifier = printer.connectionSettings.identifier

                val log =
                    when (interfaceType) {
                        InterfaceType.Lan -> "Lan: $identifier, ${printer.information?.model}, ${printer.information?.detail?.lan?.uniqueId ?: ""}"
                        InterfaceType.Bluetooth -> "Bluetooth: $identifier, ${printer.information?.model}"
                        InterfaceType.BluetoothLE -> "BluetoothLE: $identifier, ${printer.information?.model}, ${printer.information?.detail?.bluetoothLE?.deviceName ?: ""}"
                        InterfaceType.Usb -> "Usb: $identifier, ${printer.information?.model}"
                        else -> "Unknown: $identifier"
                    }
                Log.d("Discovery", "Found printer: $log")
                _logText.value += log + "\n"
            }

            override fun onDiscoveryFinished() {
                val log = "Discovery finished."
                Log.d("Discovery", log)

                _isProcessing.value = false
            }
        }
}
