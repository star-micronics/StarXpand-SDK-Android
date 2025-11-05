package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.RuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class ReceiptSample09_Order1_Template {
    companion object {
        fun createReceiptTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Center)
                                    .styleLineSpace(0.0)
                                    .styleMagnification(MagnificationParameter(2,2))
                                    .actionPrintText(
                                        "\${header}\n"
                                    )
                                    .styleBold(true)
                                    .styleInvert(true)
                                    .actionPrintText(
                                        "\${title}\n",
                                        TextParameter()
                                            .setWidth(
                                                24,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Center)
                                            )
                                    )
                            )
                            .actionFeed(2.0)
                            .actionPrintText(
                                "#\${number}",
                                TextParameter()
                                    .setWidth(10)
                            )
                            .actionPrintText(
                                "\${datetime}\n",
                                TextParameter()
                                    .setWidth(
                                        38,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "\${store_name}",
                                TextParameter()
                                    .setWidth(20)
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "\${order_number}\n",
                                TextParameter()
                                    .setWidth(
                                        28,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintText(
                                "\${customer_name}\n"
                            )
                            .actionFeedLine(1)
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .actionPrintText(
                                        "# Item"
                                    )
                                    .actionPrintText(
                                        "Cst.#\n",
                                        TextParameter()
                                            .setWidth(
                                                42,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
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
                                    .actionPrintText(
                                        "\${item_list.quantity}",
                                        TextParameter()
                                            .setWidth(2)
                                    )
                                    .actionPrintText(
                                        "\${item_list.name}",
                                        TextParameter()
                                            .setWidth(41)
                                    )
                                    .actionPrintText(
                                        "\${item_list.cost}\n",
                                        TextParameter()
                                            .setWidth(
                                                5,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}