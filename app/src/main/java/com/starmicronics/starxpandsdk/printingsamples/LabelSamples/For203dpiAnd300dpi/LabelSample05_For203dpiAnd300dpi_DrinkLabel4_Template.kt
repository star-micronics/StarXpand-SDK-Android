package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.CjkCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.RuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample05_For203dpiAnd300dpi_DrinkLabel4_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    // Change the printable area setting for this layout according to the printer resolution.
                    // 72.0 for 203dpi, 48.7 for 300dpi
                    .settingPrintableArea(72.0)
                    //.settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            .styleCjkCharacterPriority(listOf(CjkCharacterType.Japanese))
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(1, 2))
                                    .actionPrintText(
                                        "\${order_types}"
                                    )
                            )
                            .styleHorizontalPositionTo(0.0)
                            .actionPrintText(
                                "\${datetime}\n",
                                TextParameter()
                                    .setWidth(
                                        48,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .add(
                                PrinterBuilder()
                                    .styleInvert(true)
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "\${order_number}",
                                        TextParameter()
                                            .setWidth(4)
                                    )
                            )
                            .actionPrintText(
                                "(\${item_number}/\${number_of_items})",
                                TextParameter()
                                    .setWidth(6)
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "\${time}\n",
                                        TextParameter()
                                            .setWidth(
                                                17,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(1, 2))
                                    .actionPrintText(
                                        "\${product_name}\n"
                                    )
                            )
                            .add(
                                PrinterBuilder(
                                    PrinterParameter()
                                        .setTemplateExtension(
                                            TemplateExtensionParameter()
                                                .setEnableArrayFieldData(true)
                                        )
                                )
                                    .styleHorizontalPositionTo(1.0)
                                    .actionPrintText(
                                        "\${item_list.detail}\n"
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .actionPrintText(
                                "\${note}\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}