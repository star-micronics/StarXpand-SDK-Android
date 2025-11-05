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
class ReceiptSample04_FoodDelivery2_Template {
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
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(2,2))
                                    .actionPrintText(
                                        "\${store_name}\n"
                                    )
                                    .styleInvert(true)
                                    .actionPrintText(
                                        "\${order_number}",
                                        TextParameter()
                                            .setWidth(
                                                7,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Left)
                                            )
                                    )
                                    .styleBold(false)
                                    .actionPrintText(
                                        "\${customer_name}\n",
                                        TextParameter()
                                            .setWidth(
                                                17,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                            )
                            .actionPrintText(
                                "Placed at \${placed_at}\n"
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "Due at \${due_at}"
                            )
                            .styleBold(false)
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .add(
                                PrinterBuilder()
                                    .actionFeed(4.0)
                                    .styleAlignment(Alignment.Center)
                                    .styleMagnification(MagnificationParameter(2,2))
                                    .actionPrintText(
                                        "\${order_types}\n"
                                    )
                                    .actionFeed(2.0)
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .actionPrintText(
                                "Disposable items:\${disposable_items}\n"
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
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
                                        "\${item_list.quantity} x \${item_list.name}",
                                        TextParameter()
                                            .setWidth(39)
                                    )
                                    .actionPrintText(
                                        " "
                                    )
                                    .styleBold(false)
                                    .actionPrintText(
                                        "$\${item_list.price%.2f}\n",
                                        TextParameter()
                                            .setWidth(
                                                8,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                                    .actionPrintText(
                                        "\${item_list.detail1}" +
                                                "\${item_list.detail2}" +
                                                "\${item_list.detail3}" +
                                                "\${item_list.detail4}"
                                    )
                                    .actionFeed(1.0)
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .actionPrintText(
                                "Subtotal"
                            )
                            .actionPrintText(
                                "$\${subtotal%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        40,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "Amount paid"
                            )
                            .actionPrintText(
                                "$\${total%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        37,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .styleBold(false)
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .styleAlignment(Alignment.Center)
                            .actionPrintText(
                                "\${note}"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}