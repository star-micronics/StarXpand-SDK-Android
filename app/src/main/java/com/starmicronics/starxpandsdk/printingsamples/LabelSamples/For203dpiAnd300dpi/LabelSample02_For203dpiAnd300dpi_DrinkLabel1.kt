package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class LabelSample02_For203dpiAnd300dpi_DrinkLabel1 {
    companion object {
        fun createDrinkLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .addPrinter(
                        PrinterBuilder()
                            .styleBold(true)
                            .actionPrintText(
                                "Item:   1 of 3\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(1, 2))
                                    .actionPrintText(
                                        "* Jane Smith *\n" +
                                                "Gr Icd Coffee\n"
                                    )
                            )
                            .actionPrintText(
                                "No Classic\n" +
                                        "With Whole Milk\n" +
                                        "\n" +
                                        "Time:   4:14:29 PM\n" +
                                        "Reg:    9\n" +
                                        "\n" +
                                        "--------------------------------\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .actionPrintText(
                                        ">MOBILE<\n"
                                    )
                            )
                            .actionPrintText(
                                "--------------------------------\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )

            return builder.getCommands()
        }
    }
}