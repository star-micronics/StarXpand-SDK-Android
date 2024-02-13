package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.RuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class LabelSample09_FoodPrepLabel {
    companion object {
        fun createFoodPrepLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .addPrinter(
                        PrinterBuilder()
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Right)
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "WEDNESDAY\n"
                                    )
                            )
                            .styleAlignment(Alignment.Left)
                            .actionPrintText(
                                "Product\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "Lettuce\n"
                                    )
                            )
                            .actionPrintText(
                                "Prepared On\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "3/24/2021\n"
                                    )
                            )
                            .actionPrintText(
                                "Used by\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "3/25/2021\n"
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(48.0)
                            )
                            .actionPrintText(
                                "User: A. Star   Manager: M. Star\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )

            return builder.getCommands()
        }
    }
}