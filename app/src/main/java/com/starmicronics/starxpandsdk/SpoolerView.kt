package com.starmicronics.starxpandsdk

import android.content.Context
import android.graphics.BitmapFactory
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.starmicronics.stario10.InterfaceType
import com.starmicronics.stario10.StarConnectionSettings
import com.starmicronics.stario10.StarPrinter
import com.starmicronics.stario10.StarSpoolJobSettings
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.ImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.InternationalCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeLevel
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeParameter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Preview(showBackground = true)
@Composable
fun SpoolerView(
    paddingValues: PaddingValues = WindowInsets.systemBars.asPaddingValues(),
    viewModel: SpoolerViewModel = viewModel(),
) {
    val context = LocalContext.current
    var interfaceType by rememberSaveable { mutableStateOf(DefaultConnectionSettings.interfaceType) }
    var identifier by rememberSaveable { mutableStateOf(DefaultConnectionSettings.identifier) }

    val logText = viewModel.logText.collectAsState()
    var jobId by rememberSaveable { mutableIntStateOf(-1) }

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
                 viewModel.printing(
                    StarConnectionSettings(
                        interfaceType = interfaceType,
                        identifier = identifier,
                    ),
                    context,
                    onGetJobId = { id -> jobId = id },
                )
            },
            modifier =
                Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            Text(stringResource(id = R.string.spoolPrint))
        }

        Row(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            TextField(
                value = jobId.toString(),
                onValueChange = { newValue ->
                    val parsed = newValue.toIntOrNull()
                    if (parsed != null) {
                        jobId = parsed
                    }
                },
                label = { Text(stringResource(id = R.string.jobId)) },
                keyboardOptions =
                    KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                    ),
                modifier =
                    Modifier
                        .padding(8.dp)
                        .weight(1f),
            )

            Button(
                onClick = {
                    viewModel.getJobStatus(
                        StarConnectionSettings(
                            interfaceType = interfaceType,
                            identifier = identifier,
                        ),
                        jobId,
                        context,
                    )
                },
                modifier =
                    Modifier
                        .padding(8.dp)
                        .weight(1f),
            ) {
                Text(stringResource(id = R.string.getJobStatus))
            }
        }

        Text(
            text = logText.value,
            modifier = Modifier.padding(8.dp),
        )
    }
}

class SpoolerViewModel: ViewModel() {
    private val _logText = MutableStateFlow("")
    val logText: StateFlow<String> get() = _logText

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> get() = _isProcessing

    fun printing(
        starConnectionSettings: StarConnectionSettings,
        context: Context,
        onGetJobId: ((Int) -> Unit)? = null,
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            var log = ""
            _logText.value = log

            var getJobId = -1
            val printer = StarPrinter(starConnectionSettings, context)
            try {
                val commands = createCommand(context)
                printer.openAsync().await()

                val jobSettings = StarSpoolJobSettings(true, 30, "Print from Android")
                getJobId = printer.printAsync(commands, jobSettings).await()

                log = "Success"
            } catch (e: Exception) {
                log = "Error: ${e.message}"
            } finally {
                printer.closeAsync().await()
            }
            Log.d("Spooler", log)
            Log.d("Spooler", "jobId: $getJobId")
            _logText.value = log
            onGetJobId?.invoke(getJobId)
            _isProcessing.value = false
        }
    }

    fun getJobStatus(
        starConnectionSettings: StarConnectionSettings,
        jobId: Int,
        context: Context,
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            var log = ""
            _logText.value = log

            val printer = StarPrinter(starConnectionSettings, context)
            try {
                printer.openAsync().await()
                val jobStatus = printer.getSpoolJobStatusAsync(jobId).await()

                log = jobStatus.toString().replace(",", "\n")
            } catch (e: Exception) {
                log = "Error: ${e.message}"
            } finally {
                printer.closeAsync().await()

                Log.d("Spooler", log)
                _logText.value = log + "\n\n"
            }
            _isProcessing.value = false
        }
    }

    private fun createCommand(context: Context): String {
        val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)

        val builder = StarXpandCommandBuilder()
        builder.addDocument(
            DocumentBuilder()
                // To open a cash drawer, comment out the following code.
//                .addDrawer(
//                  DrawerBuilder()
//                      .actionOpen(OpenParameter())
//                )
                .addPrinter(
                    PrinterBuilder()
                        .actionPrintImage(ImageParameter(logo, 406))
                        .styleInternationalCharacter(InternationalCharacterType.Usa)
                        .styleCharacterSpace(0.0)
                        .styleAlignment(com.starmicronics.stario10.starxpandcommand.printer.Alignment.Center)
                        .actionPrintText(
                            "Star Clothing Boutique\n" +
                                "123 Star Road\n" +
                                "City, State 12345\n" +
                                "\n",
                        ).styleAlignment(com.starmicronics.stario10.starxpandcommand.printer.Alignment.Left)
                        .actionPrintText(
                            "Date:MM/DD/YYYY    Time:HH:MM PM\n" +
                                "--------------------------------\n" +
                                "\n",
                        ).add(
                            PrinterBuilder()
                                .styleBold(true)
                                .actionPrintText("SALE\n"),
                        ).actionPrintText(
                            "SKU         Description    Total\n" +
                                "300678566   PLAIN T-SHIRT  10.99\n" +
                                "300692003   BLACK DENIM    29.99\n" +
                                "300651148   BLUE DENIM     29.99\n" +
                                "300642980   STRIPED DRESS  49.99\n" +
                                "300638471   BLACK BOOTS    35.99\n" +
                                "\n" +
                                "Subtotal                  156.95\n" +
                                "Tax                         0.00\n" +
                                "--------------------------------\n",
                        ).actionPrintText("Total     ")
                        .add(
                            PrinterBuilder()
                                .styleMagnification(MagnificationParameter(2, 2))
                                .actionPrintText("   $156.95\n"),
                        ).actionPrintText(
                            "--------------------------------\n" +
                                "\n" +
                                "Charge\n" +
                                "156.95\n" +
                                "Visa XXXX-XXXX-XXXX-0123\n" +
                                "\n",
                        ).add(
                            PrinterBuilder()
                                .styleInvert(true)
                                .actionPrintText("Refunds and Exchanges\n"),
                        ).actionPrintText("Within ")
                        .add(
                            PrinterBuilder()
                                .styleUnderLine(true)
                                .actionPrintText("30 days"),
                        ).actionPrintText(" with receipt\n")
                        .actionPrintText(
                            "And tags attached\n" +
                                "\n",
                        ).styleAlignment(com.starmicronics.stario10.starxpandcommand.printer.Alignment.Center)
                        .actionPrintBarcode(
                            BarcodeParameter("0123456", BarcodeSymbology.Jan8)
                                .setBarDots(3)
                                .setHeight(5.0)
                                .setPrintHri(true),
                        ).actionFeedLine(1)
                        .actionPrintQRCode(
                            QRCodeParameter("Hello, World\n")
                                .setLevel(QRCodeLevel.L)
                                .setCellSize(8),
                        ).actionCut(CutType.Partial),
                ),
        )
        return builder.getCommands()
    }
}
