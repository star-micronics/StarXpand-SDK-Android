package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class Sample05_FoodDelivery {
    companion object {
        fun createFoodDeliveryReceipt(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .addPrinter(
                        PrinterBuilder()
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(3, 3))
                                    .actionPrintText(
                                        "Star Eats\n"
                                    )
                                    .add(
                                        PrinterBuilder()
                                            .styleInvert(true)
                                            .actionPrintText(
                                                "8A720  Micronics\n"
                                            )
                                    )
                            )
                            .styleAlignment(Alignment.Center)
                            .actionPrintText(
                                "Placed at March 24 2021 1:30PM\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleUnderLine(true)
                                    .actionPrintText(
                                        "                                                \n"
                                    )
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "DELIVERY\n"
                                    )
                            )
                            .add(
                                PrinterBuilder()
                                    .styleUnderLine(true)
                                    .actionPrintText(
                                        "                                                \n"
                                    )
                            )
                            .styleAlignment(Alignment.Left)
                            .actionPrintText(
                                "1XStar's lunch box A *                    $10.95\n" +
                                        "------------------------------------------------\n" +
                                        "Subtotal                                   $0.97\n" +
                                        "Amount paid                               $11.92\n" +
                                        "item 1                                    $10.00\n" +
                                        "------------------------------------------------\n" +
                                        "*Use special source as you like!\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )

            return builder.getCommands()
        }
    }
}