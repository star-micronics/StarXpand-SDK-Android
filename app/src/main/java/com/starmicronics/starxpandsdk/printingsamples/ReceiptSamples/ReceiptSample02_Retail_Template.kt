package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.RuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class ReceiptSample02_Retail_Template {
    companion object {
        fun createReceiptTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .add(
                                PrinterBuilder()
                                    .styleInvert(true)
                                    .styleMagnification(MagnificationParameter(2,2))
                                    .actionPrintText(
                                        "\${store_name}\n",
                                        TextParameter()
                                            .setWidth(
                                                24,
                                                TextWidthParameter().setAlignment(TextAlignment.Center)
                                            )
                                    )
                            )
                            .styleAlignment(Alignment.Left)
                            .actionPrintText(
                                "\${order_number}",
                                TextParameter()
                                    .setWidth(24)
                            )
                            .actionPrintText(
                                "\${datetime}\n",
                                TextParameter()
                                    .setWidth(
                                        24,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintText(
                                "\${order_types}",
                                TextParameter()
                                    .setWidth(24)
                            )
                            .actionPrintText(
                                "Served by \${staff_name}\n",
                                TextParameter()
                                    .setWidth(
                                        24,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintText(
                                "Transaction \${transaction}\n"
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
                                    .actionPrintText(
                                        "\${item_list.quantity} x \${item_list.name}",
                                        TextParameter()
                                            .setWidth(37)
                                    )
                                    .actionPrintText(
                                        " "
                                    )
                                    .actionPrintText(
                                        "\${item_list.price%8.2f} T\n",
                                        TextParameter()
                                            .setWidth(
                                                10,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                                    .actionPrintText(
                                        "\${item_list.detail}"
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .actionPrintText(
                                "Subtotal"
                            )
                            .actionPrintText(
                                "\${subtotal_price%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        40,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintText(
                                "Tax"
                            )
                            .actionPrintText(
                                "\${tax%8.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        45,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "Total"
                            )
                            .actionPrintText(
                                "\${total_price%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        43,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .styleBold(false)
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .actionPrintText(
                                "\${payment_method}",
                                TextParameter()
                                    .setWidth(24)
                            )
                            .actionPrintText(
                                "\${payment_amount%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        24,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .styleAlignment(Alignment.Center)
                            .actionPrintText(
                                "\${address}\n" +
                                        "\${telephone_number}\n" +
                                        "\${footer}\n"
                            )
                            .actionPrintBarcode(
                                BarcodeParameter("\${barcode}", BarcodeSymbology.Code128)
                                    .setBarDots(1)
                                    .setPrintHri(true)
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}