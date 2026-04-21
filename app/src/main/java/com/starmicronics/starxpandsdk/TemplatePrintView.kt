package com.starmicronics.starxpandsdk

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.starmicronics.stario10.InterfaceType
import com.starmicronics.stario10.StarConnectionSettings
import com.starmicronics.stario10.StarPrinter
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.FontType
import com.starmicronics.stario10.starxpandcommand.printer.ImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.InternationalCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModePrintDirection
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.RuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextEllipsizeType
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Scanner

@Preview(showBackground = true)
@Composable
fun TemplatePrintView(
    paddingValues: PaddingValues = WindowInsets.systemBars.asPaddingValues(),
    viewModel: TemplatePrintViewModel = viewModel(),
) {
    val context = LocalContext.current
    var interfaceType by rememberSaveable { mutableStateOf(DefaultConnectionSettings.interfaceType) }
    var identifier by rememberSaveable { mutableStateOf(DefaultConnectionSettings.identifier) }

    val logText = viewModel.logText.collectAsState()

    val templateData = stringArrayResource(id = R.array.list_template)
    val selectedTemplateIndex = remember { mutableIntStateOf(0) }

    val fieldData =
        when (selectedTemplateIndex.intValue) {
            0 -> stringArrayResource(id = R.array.list_receipt_with_specifying_number_of_characters_field_data)
            1 -> stringArrayResource(id = R.array.list_receipt_without_specifying_number_of_characters_field_data)
            else -> stringArrayResource(id = R.array.list_label_field_data)
        }
    val selectedFieldData = remember { mutableIntStateOf(0) }

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

        Text(
            text = "Template",
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
        )

        Column(modifier = Modifier) {
            templateData.forEachIndexed { index, label ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = (index == selectedTemplateIndex.intValue),
                        onClick = {
                            selectedTemplateIndex.intValue = index
                        },
                    )
                    Text(
                        text = label,
                    )
                }
            }
        }

        Text(
            text = "Field Data",
            modifier =
                Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
        )

        Column(modifier = Modifier) {
            fieldData.forEachIndexed { index, label ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(36.dp)
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    RadioButton(
                        selected = (index == selectedFieldData.intValue),
                        onClick = {
                            selectedFieldData.intValue = index
                        },
                    )
                    Text(
                        text = label,
                    )
                }
            }
        }

        Button(
            onClick = {
                viewModel.printing(
                    StarConnectionSettings(
                        interfaceType,
                        identifier,
                    ),
                    selectedTemplateIndex.intValue,
                    selectedFieldData.intValue,
                    context,
                )
            },
            modifier =
                Modifier
                    .padding(horizontal = 8.dp, vertical = 4.dp),
        ) {
            Text("Print")
        }

        Text(
            text = logText.value,
            modifier = Modifier.padding(8.dp),
        )
    }
}

class TemplatePrintViewModel(): ViewModel() {
    private val _logText = MutableStateFlow("")
    val logText: StateFlow<String> get() = _logText

    private val _isProcessing = MutableStateFlow(false)
    val isProcessing: StateFlow<Boolean> get() = _isProcessing

    fun printing(
        starConnectionSettings: StarConnectionSettings,
        selectedTemplate: Int,
        selectedFieldData: Int,
        context: Context,
    ) {
        viewModelScope.launch {
            _isProcessing.value = true
            var log = ""
            _logText.value = log

            val printer = StarPrinter(starConnectionSettings, context)
            try {
                val template =
                    when (selectedTemplate) {
                        0 -> createReceiptWithSpecifyingNumberOfCharactersTemplate(context)
                        1 -> createReceiptWithoutSpecifyingNumberOfCharactersTemplate(context)
                        else -> createLabelTemplate()
                    }
                val inputStream =
                    context.resources.openRawResource(
                        when (selectedTemplate) {
                            0 if selectedFieldData == 0 -> R.raw.receipt1_field_data
                            0 if selectedFieldData == 1 -> R.raw.receipt2_field_data
                            1 if selectedFieldData == 0 -> R.raw.receipt1_field_data
                            1 if selectedFieldData == 1 -> R.raw.receipt3_field_data
                            2 if selectedFieldData == 0 -> R.raw.label1_field_data
                            else -> R.raw.label2_field_data
                        },
                    )
                val fieldData: String = Scanner(inputStream).useDelimiter("\\A").next()

                printer.template = template

                printer.openAsync().await()
                printer.printAsync(fieldData).await()

                log = "Success"
            } catch (e: Exception) {
                log = "Error: ${e.message}"
            } finally {
                printer.closeAsync().await()
            }
            Log.d("Printing", log)
            _logText.value = log
            _isProcessing.value = false
        }
    }

    private fun createReceiptWithSpecifyingNumberOfCharactersTemplate(context: Context): String {
        val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)
        val signature = BitmapFactory.decodeResource(context.resources, R.drawable.signature)

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
                                .styleAlignment(com.starmicronics.stario10.starxpandcommand.printer.Alignment.Center)
                                .styleBold(true)
                                .styleInvert(true)
                                .styleMagnification(MagnificationParameter(2, 2))
                                .actionPrintText($$"${store_name}\n"),
                        ).actionFeed(1.0)
                        .actionPrintText(
                            $$"Order ${order_number}",
                            TextParameter()
                                .setWidth(
                                    16,
                                ),
                        ).actionPrintText(" ")
                        .actionPrintText(
                            $$"${time}\n",
                            TextParameter()
                                .setWidth(
                                    15,
                                    TextWidthParameter()
                                        .setAlignment(TextAlignment.Right)
                                        .setEllipsizeType(TextEllipsizeType.End),
                                ),
                        ).actionPrintText(
                            $$"Sale for ${sales_type}",
                            TextParameter()
                                .setWidth(16),
                        ).actionPrintText(" ")
                        .actionPrintText(
                            $$"Served by ${server}\n",
                            TextParameter()
                                .setWidth(
                                    15,
                                    TextWidthParameter()
                                        .setAlignment(TextAlignment.Right)
                                        .setEllipsizeType(TextEllipsizeType.End),
                                ),
                        ).actionPrintText(
                            $$"Transaction #${transaction_id}\n",
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).add(
                            PrinterBuilder(
                                PrinterParameter()
                                    .setTemplateExtension(
                                        TemplateExtensionParameter()
                                            .setEnableArrayFieldData(true),
                                    ),
                            ).actionPrintText(
                                $$"${item_list.quantity}",
                                TextParameter()
                                    .setWidth(2),
                            ).actionPrintText(
                                $$"${item_list.name}",
                                TextParameter()
                                    .setWidth(24),
                            ).actionPrintText(
                                $$"${item_list.unit_price%6.2lf}\n",
                                TextParameter()
                                    .setWidth(6),
                            ),
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionPrintText(
                            "Subtotal",
                            TextParameter()
                                .setWidth(26),
                        ).actionPrintText(
                            $$"${subtotal%6.2lf}\n",
                            TextParameter()
                                .setWidth(
                                    6,
                                    TextWidthParameter()
                                        .setEllipsizeType(TextEllipsizeType.End),
                                ),
                        ).actionPrintText(
                            "Tax",
                            TextParameter()
                                .setWidth(26),
                        ).actionPrintText(
                            $$"${tax%6.2lf}\n",
                            TextParameter()
                                .setWidth(
                                    6,
                                    TextWidthParameter()
                                        .setEllipsizeType(TextEllipsizeType.End),
                                ),
                        ).add(
                            PrinterBuilder()
                                .styleBold(true)
                                .actionPrintText(
                                    "Total",
                                    TextParameter()
                                        .setWidth(26),
                                ).actionPrintText(
                                    $$"${total%6.2lf}\n",
                                    TextParameter()
                                        .setWidth(
                                            6,
                                            TextWidthParameter()
                                                .setEllipsizeType(TextEllipsizeType.End),
                                        ),
                                ),
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionPrintText(
                            $$"${credit_card_number}",
                            TextParameter()
                                .setWidth(26),
                        ).actionPrintText(
                            $$"${total%6.2lf}\n",
                            TextParameter()
                                .setWidth(
                                    6,
                                    TextWidthParameter()
                                        .setEllipsizeType(TextEllipsizeType.End),
                                ),
                        ).actionPrintText(
                            "Approval Code",
                            TextParameter()
                                .setWidth(16),
                        ).actionPrintText(
                            $$"${approval_code}\n",
                            TextParameter()
                                .setWidth(
                                    16,
                                    TextWidthParameter()
                                        .setAlignment(TextAlignment.Right),
                                ),
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionPrintText(
                            "Amount",
                            TextParameter()
                                .setWidth(26),
                        ).actionPrintText(
                            $$"${amount%6.2lf}\n",
                            TextParameter()
                                .setWidth(
                                    6,
                                    TextWidthParameter()
                                        .setEllipsizeType(TextEllipsizeType.End),
                                ),
                        ).actionPrintText(
                            "Total",
                            TextParameter()
                                .setWidth(26),
                        ).actionPrintText(
                            $$"${total%6.2lf}\n",
                            TextParameter()
                                .setWidth(
                                    6,
                                    TextWidthParameter()
                                        .setEllipsizeType(TextEllipsizeType.End),
                                ),
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionPrintText(
                            "Signature\n",
                        ).add(
                            PrinterBuilder()
                                .styleAlignment(com.starmicronics.stario10.starxpandcommand.printer.Alignment.Center)
                                .actionPrintImage(
                                    ImageParameter(signature, 256),
                                ),
                        ).actionPrintRuledLine(
                            RuledLineParameter(32.0)
                                .setX(8.0),
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionPrintText("\n")
                        .styleAlignment(com.starmicronics.stario10.starxpandcommand.printer.Alignment.Center)
                        .actionPrintText(
                            $$"${address}\n",
                        ).actionPrintText(
                            $$"${tel}\n",
                        ).actionPrintText(
                            $$"${mail}\n",
                        ).actionFeed(1.0)
                        .actionPrintText(
                            $$"${url}\n",
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionFeed(2.0)
                        .actionPrintText(
                            "Powered by Star Micronics\n",
                        ).actionPrintBarcode(
                            BarcodeParameter($$"${transaction_id}", BarcodeSymbology.Code128)
                                .setPrintHri(true),
                        ).actionCut(CutType.Partial),
                ),
        )

        return builder.getCommands()
    }

    private fun createReceiptWithoutSpecifyingNumberOfCharactersTemplate(context: Context): String {
        val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)
        val signature = BitmapFactory.decodeResource(context.resources, R.drawable.signature)

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
                                .styleAlignment(com.starmicronics.stario10.starxpandcommand.printer.Alignment.Center)
                                .styleBold(true)
                                .styleInvert(true)
                                .styleMagnification(MagnificationParameter(2, 2))
                                .actionPrintText($$"${store_name}\n"),
                        ).actionFeed(1.0)
                        .styleHorizontalTabPositions(listOf(17))
                        .actionPrintText(
                            $$"Order ${order_number}\t${time}\n",
                        ).actionPrintText(
                            $$"Sale for ${sales_type}\tServed by ${server}\n",
                        ).actionPrintText(
                            $$"Transaction #${transaction_id}\n",
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).add(
                            PrinterBuilder(
                                PrinterParameter()
                                    .setTemplateExtension(
                                        TemplateExtensionParameter()
                                            .setEnableArrayFieldData(true),
                                    ),
                            ).styleHorizontalTabPositions(listOf(2, 26))
                                .actionPrintText(
                                    $$"${item_list.quantity}\t${item_list.name}\t${item_list.unit_price%6.2lf}\n",
                                ),
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).styleHorizontalTabPositions(listOf(26))
                        .actionPrintText(
                            $$"Subtotal\t${subtotal%6.2lf}\n",
                        ).actionPrintText(
                            $$"Tax\t${tax%6.2lf}\n",
                        ).add(
                            PrinterBuilder()
                                .styleBold(true)
                                .actionPrintText(
                                    $$"Total\t${total%6.2lf}\n",
                                ),
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionPrintText(
                            $$"${credit_card_number}\t${total%6.2lf}\n",
                        ).actionPrintText(
                            $$"Approval Code\t${approval_code}\n",
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionPrintText(
                            $$"Amount\t${amount%6.2lf}\n",
                        ).actionPrintText(
                            $$"Total\t${total%6.2lf}\n",
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionPrintText(
                            "Signature\n",
                        ).add(
                            PrinterBuilder()
                                .styleAlignment(com.starmicronics.stario10.starxpandcommand.printer.Alignment.Center)
                                .actionPrintImage(
                                    ImageParameter(signature, 256),
                                ),
                        ).actionPrintRuledLine(
                            RuledLineParameter(32.0)
                                .setX(8.0),
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionPrintText("\n")
                        .styleAlignment(com.starmicronics.stario10.starxpandcommand.printer.Alignment.Center)
                        .actionPrintText(
                            $$"${address}\n",
                        ).actionPrintText(
                            $$"${tel}\n",
                        ).actionPrintText(
                            $$"${mail}\n",
                        ).actionFeed(1.0)
                        .actionPrintText(
                            $$"${url}\n",
                        ).actionPrintRuledLine(
                            RuledLineParameter(48.0),
                        ).actionFeed(2.0)
                        .actionPrintText(
                            "Powered by Star Micronics\n",
                        ).actionPrintBarcode(
                            BarcodeParameter($$"${transaction_id}", BarcodeSymbology.Code128)
                                .setPrintHri(true),
                        ).actionCut(CutType.Partial),
                ),
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
                                .actionPrintText($$"${item_name}\n")
                                .styleVerticalPositionTo(6.0)
                                .actionPrintBarcode(
                                    BarcodeParameter($$"${sku}", BarcodeSymbology.Ean13)
                                        .setHeight(5.0)
                                        .setPrintHri(true),
                                ).add(
                                    PageModeBuilder()
                                        .styleFont(FontType.B)
                                        .styleVerticalPositionTo(0.0)
                                        .styleHorizontalPositionTo(34.0)
                                        .actionPrintText("EUR")
                                        .styleVerticalPositionTo(0.0)
                                        .styleHorizontalPositionTo(43.0)
                                        .actionPrintText("UK"),
                                ).add(
                                    PageModeBuilder()
                                        .styleMagnification(MagnificationParameter(2, 2))
                                        .stylePrintDirection(PageModePrintDirection.BottomToTop)
                                        .styleVerticalPositionTo(35.0)
                                        .styleHorizontalPositionTo(5.0)
                                        .actionPrintText($$"${price_eur%.2lf}")
                                        .styleVerticalPositionTo(43.0)
                                        .styleHorizontalPositionTo(5.0)
                                        .actionPrintText($$"${price_gbp%.2lf}"),
                                ).addPageMode(
                                    PageModeAreaParameter(32.0, 15.0)
                                        .setY(15.0),
                                    PageModeBuilder()
                                        .actionPrintText($$"${maker_information}"),
                                ),
                        ).actionCut(CutType.Partial),
                ),
        )

        return builder.getCommands()
    }
}
