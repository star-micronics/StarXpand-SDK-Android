package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class LabelSample12_For203dpiAnd300dpi_InventoryLabel {
    companion object {
        fun createInventoryLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleUnderLine(true)
                                    .actionPrintText(
                                        "Star TSP100IV\n"
                                    )
                            )
                            .actionPrintText(
                                "P/N: 000001\n"
                            )
                            .actionPrintBarcode(
                                BarcodeParameter("2558271100", BarcodeSymbology.Code39)
                                    .setBarDots(3)
                                    .setHeight(18.0)
                                    .setPrintHri(true)
                            )
                            .actionPrintText(
                                "\n" +
                                        "ABC: WAREHOUSE\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )

            return builder.getCommands()
        }
    }
}