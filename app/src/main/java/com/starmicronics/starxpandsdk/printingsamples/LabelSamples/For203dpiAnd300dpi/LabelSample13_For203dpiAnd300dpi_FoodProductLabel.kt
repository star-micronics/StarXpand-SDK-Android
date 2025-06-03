package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class LabelSample13_For203dpiAnd300dpi_FoodProductLabel {
    companion object {
        fun createFoodProductLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .styleBold(true)
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "Star's Lunch Box -A-\n"
                                    )
                            )
                            .actionPrintText(
                                "Use special sauce as you like\n" +
                                        "------------------------------------------------\n" +
                                        "MFG 2021/4/1\n" +
                                        "------------------------------------------------\n" +
                                        "Contains Wheat, Milk, and Soy.\n" +
                                        "May Contains Sesame.\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )

            return builder.getCommands()
        }
    }
}