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

class ReceiptSample01_OnlineOrder_Template {
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
                                    .styleMagnification(MagnificationParameter(2,2))
                                    .styleBold(true)
                                    .actionPrintText(
                                        "\${store_name}\n" +
                                                "\${order_number}\n"
                                    )
                            )
                            .actionPrintText(
                                "\${name}\n"
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.3)
                            )
                            .actionFeed(1.0)
                            .actionPrintText(
                                "\${date}",
                                TextParameter()
                                    .setWidth(24)
                            )
                            .actionPrintText(
                                "\${time}\n",
                                TextParameter()
                                    .setWidth(
                                        24,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionFeed(1.0)
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .styleAlignment(Alignment.Center)
                            .actionPrintText(
                                "PICKUP \${pickup_time}\n"
                            )
                            .styleAlignment(Alignment.Left)
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .actionFeedLine(1)
                            .add(
                                PrinterBuilder(
                                    PrinterParameter()
                                        .setTemplateExtension(
                                            TemplateExtensionParameter()
                                                .setEnableArrayFieldData(true)
                                        )
                                )
                                    .styleBold(true)
                                    .actionPrintText(
                                        "\${item_list.quantity} x \${item_list.name}\n"
                                    )
                                    .styleBold(false)
                                    .actionPrintText(
                                        "\${item_list.detail}"
                                    )
                                    .actionFeedLine(1)
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .actionFeed(1.0)
                            .actionPrintText(
                                "\${note}\n"
                            )
                            .actionFeed(1.0)
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .actionPrintText(
                                "\${footer1}",
                                TextParameter()
                                    .setWidth(24)
                            )
                            .actionPrintText(
                                "\${footer2}\n",
                                TextParameter()
                                    .setWidth(
                                        24,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}