package com.starmicronics.starxpandsdk

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import com.starmicronics.stario10.InterfaceType
import com.starmicronics.stario10.MaintenanceInformationType
import com.starmicronics.stario10.StarConnectionSettings
import com.starmicronics.stario10.StarPrinter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun MaintenanceView(
    paddingValues: PaddingValues = WindowInsets.systemBars.asPaddingValues(),
    viewModel: MaintenanceViewModel = viewModel(),
) {
    val context = LocalContext.current
    var interfaceType by rememberSaveable { mutableStateOf(DefaultConnectionSettings.interfaceType) }
    var identifier by rememberSaveable { mutableStateOf(DefaultConnectionSettings.identifier) }

    // The PowerOnTimeMin counter is permanent and cannot be reset, so it is not listed here.
    val resettableTypes = MaintenanceInformationType.entries.filter { it != MaintenanceInformationType.PowerOnTimeMin }
    val resetTypes = remember { mutableStateListOf<MaintenanceInformationType>() }

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
                viewModel.getInformation(
                    StarConnectionSettings(
                        interfaceType = interfaceType,
                        identifier = identifier,
                    ),
                    context,
                )
            },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            Text(stringResource(id = R.string.get_information))
        }

        Text(
            stringResource(id = R.string.reset_information_types),
            modifier =
                Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp)
                    .fillMaxWidth(),
        )
        resettableTypes.forEach { type ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
            ) {
                Checkbox(
                    checked = resetTypes.contains(type),
                    onCheckedChange = { checked ->
                        if (checked) resetTypes.add(type) else resetTypes.remove(type)
                    },
                )
                Text(type.name)
            }
        }

        Button(
            onClick = {
                viewModel.resetInformation(
                    StarConnectionSettings(
                        interfaceType = interfaceType,
                        identifier = identifier,
                    ),
                    resetTypes.toList(),
                    context,
                )
            },
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            Text(stringResource(id = R.string.reset_information))
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
}

class MaintenanceViewModel: ViewModel() {
    private val _logText = MutableStateFlow("")
    val logText: StateFlow<String> get() = _logText

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> get() = _isProcessing

    fun getInformation(
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

                printer.setting?.maintenance?.let { maintenance ->
                    val information = maintenance.getInformationAsync().await()

                    // Information not supported by the printer is omitted from the returned data.
                    for (type in MaintenanceInformationType.entries) {
                        val value = information[type]
                        _logText.value += "${type.name}: ${value ?: "Not Supported"}\n"
                    }

                    log = "Success"
                } ?: run {
                    log = "Not Supported Model."
                }
            } catch (e: Exception) {
                log = "Error: ${e.message}"
            } finally {
                printer.closeAsync().await()
            }
            Log.d("Maintenance", log)
            _logText.value += log
            _isProcessing.value = false
        }
    }

    fun resetInformation(
        starConnectionSettings: StarConnectionSettings,
        types: List<MaintenanceInformationType>,
        context: Context,
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            var log = ""
            _logText.value = ""

            val printer = StarPrinter(starConnectionSettings, context)
            try {
                printer.openAsync().await()

                printer.setting?.maintenance?.let { maintenance ->
                    _logText.value += "Reset: ${types.joinToString(", ") { it.name }}\n\n"

                    // Once maintenance information has been reset, it cannot be restored.
                    maintenance.resetInformationAsync(types).await()

                    log = "Success"
                } ?: run {
                    log = "Not Supported Model."
                }
            } catch (e: Exception) {
                log = "Error: ${e.message}"
            } finally {
                printer.closeAsync().await()
            }
            Log.d("Maintenance", log)
            _logText.value += log
            _isProcessing.value = false
        }
    }
}
