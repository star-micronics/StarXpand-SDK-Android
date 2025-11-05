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
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthType

class ReceiptSample10_Order2_Template {
    companion object {
        fun createReceiptTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleCjkCharacterPriority(listOf(CjkCharacterType.Japanese))
                            .actionPrintText(
                                "\${time}",
                                TextParameter()
                                    .setWidth(8)
                            )
                            .actionPrintText(
                                "\${staff_name}",
                                TextParameter()
                                    .setWidth(
                                        20,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                            .setWidthType(TextWidthType.Full)
                                    )
                            )
                            .styleMagnification(MagnificationParameter(2,2))
                            .styleInvert(true)
                            .actionPrintText(
                                " \${classification} "
                            )
                            .styleInvert(false)
                            .actionPrintText(
                                " \${table_number}",
                                TextParameter()
                                    .setWidth(10)
                            )
                            .add(
                                PrinterBuilder()
                                    .styleLineSpace(0.0)
                                    .styleMagnification(MagnificationParameter(1,1))
                                    .actionPrintText(
                                        "\${visitors}名\n",
                                        TextParameter()
                                            .setWidth(
                                                20,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                            )
                            .actionFeed(0.5)
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                            )
                            .add(
                                PrinterBuilder(
                                    PrinterParameter()
                                        .setTemplateExtension(
                                            TemplateExtensionParameter()
                                                .setEnableArrayFieldData(true)
                                        )
                                )
                                    .actionPrintText(
                                        "\${item_list.name}",
                                        TextParameter()
                                            .setWidth(22)
                                    )
                                    .actionPrintText(
                                        "\${item_list.quantity}\n",
                                        TextParameter()
                                            .setWidth(
                                                2,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}