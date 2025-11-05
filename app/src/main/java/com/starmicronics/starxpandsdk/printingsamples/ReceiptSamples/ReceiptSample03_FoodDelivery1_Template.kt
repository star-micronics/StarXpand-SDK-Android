package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class ReceiptSample03_FoodDelivery1_Template {
    companion object {
        fun createReceiptTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .styleBold(true)
                            .styleMagnification(MagnificationParameter(2,2))
                            .actionPrintText(
                                "\${store_name}\n",
                                TextParameter()
                                    .setWidth(
                                        24,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Center)
                                    )
                            )
                            .styleAlignment(Alignment.Left)
                            .styleBold(false)
                            .actionPrintText(
                                "------------------------"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(1,1))
                                    .actionPrintText(
                                        "Customer Name\n"
                                    )
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "\${customer_name}\n"
                            )
                            .styleBold(false)
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(1,1))
                                    .actionPrintText(
                                        "Delivery\n"
                                    )
                            )
                            .actionPrintText(
                                "\${staff_name}\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(1,1))
                                    .actionPrintText(
                                        "Delivery Pickup Time\n"
                                    )
                            )
                            .actionPrintText(
                                "\${pickup_time}\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(1,1))
                                    .actionPrintText(
                                        "Order Number\n"
                                    )
                            )
                            .actionPrintText(
                                "\${order_number}\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(1,1))
                                    .actionPrintText(
                                        "Total Items\n"
                                    )
                            )
                            .actionPrintText(
                                "\${total_items} items\n"
                            )
                            .actionPrintText(
                                "------------------------"
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
                                        "\${item_list.quantity}x\${item_list.name}",
                                        TextParameter()
                                            .setWidth(18)
                                    )
                                    .actionPrintText(
                                        " "
                                    )
                                    .actionPrintText(
                                        "$\${item_list.price%.2f}\n",
                                        TextParameter()
                                            .setWidth(
                                                5,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right))
                                    )
                                    .actionPrintText(
                                        "\${item_list.detail}"
                                    )
                                    .actionPrintText(
                                        "------------------------\n"
                                    )
                            )
                            .actionPrintText(
                                "\${total_items} totalitems\n"
                            )
                            .actionPrintText(
                                "Subtotal"
                            )
                            .actionPrintText(
                                "$\${subtotal%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        16,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "Total"
                            )
                            .actionPrintText(
                                "$\${total%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        19,
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