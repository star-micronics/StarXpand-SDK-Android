package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRectangleParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.RuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class Sample11_DeliLabel {
    companion object {
        fun createDeliLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "Star Grocery Store\n"
                                    )
                            )
                            .actionPrintText(
                                "123 Star road, City, State 12345\n" +
                                        "\n"
                            )
                            .styleAlignment(Alignment.Left)
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "Roast Beef\n"
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                            )
                            .addPageMode(
                                PageModeAreaParameter(72.0, 32.0)
                                    .setY(3.0),
                                PageModeBuilder()
                                    .styleVerticalPositionBy(16.0)
                                    .actionPrintBarcode(BarcodeParameter("21234567890", BarcodeSymbology.UpcA)
                                        .setBarDots(3)
                                        .setHeight(10.0)
                                        .setPrintHri(true)
                                    )
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(38.0, 0.0, 34.0, 27.0)
                                            .setRoundCorner(true)
                                            .setCornerRadius(3.0)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(38.0, 13.5, 72.0, 13.5)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(55.0, 0.0, 55.0, 13.5)
                                    )
                                    .styleHorizontalPositionTo(45.0)
                                    .styleVerticalPositionTo(2.0)
                                    .actionPrintText(
                                        "$/lb\n"
                                    )
                                    .add(
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(45.0)
                                            .styleVerticalPositionTo(8.0)
                                            .styleBold(true)
                                            .styleMagnification(MagnificationParameter(1, 2))
                                            .actionPrintText(
                                                "4.99\n"
                                            )
                                    )
                                    .styleHorizontalPositionTo(58.0)
                                    .styleVerticalPositionTo(2.0)
                                    .actionPrintText(
                                        "weight\n"
                                    )
                                    .add(
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(58.0)
                                            .styleVerticalPositionTo(8.0)
                                            .styleBold(true)
                                            .styleMagnification(MagnificationParameter(1, 2))
                                            .actionPrintText(
                                                "0.24 lb\n"
                                            )
                                    )
                                    .styleHorizontalPositionTo(62.0)
                                    .styleVerticalPositionTo(15.5)
                                    .actionPrintText(
                                        "Price\n"
                                    )
                                    .styleHorizontalPositionTo(60.0)
                                    .styleVerticalPositionTo(21.5)
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(1, 2))
                                    .actionPrintText(
                                        "$ 1.20\n"
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )

            return builder.getCommands()
        }
    }
}