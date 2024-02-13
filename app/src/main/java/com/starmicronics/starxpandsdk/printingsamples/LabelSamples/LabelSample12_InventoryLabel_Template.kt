package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class LabelSample12_InventoryLabel_Template {
    companion object {
        fun createInventoryLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleUnderLine(true)
                                    .actionPrintText(
                                        "\${name}\n"
                                    )
                            )
                            .actionPrintText(
                                "P/N: \${parts_number%06u}\n"
                            )
                            .actionPrintBarcode(
                                BarcodeParameter("\${sku}", BarcodeSymbology.Code39)
                                    .setBarDots(3)
                                    .setHeight(18.0)
                                    .setPrintHri(true)
                            )
                            .actionPrintText(
                                "\n" +
                                        "ABC: \${abc}\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}