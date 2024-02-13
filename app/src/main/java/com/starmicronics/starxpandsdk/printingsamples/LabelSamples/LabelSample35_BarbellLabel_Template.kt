package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample35_BarbellLabel_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(55.0)
                    .addPrinter(
                        PrinterBuilder()
                            .addPageMode(
                                PageModeAreaParameter(55.0, 27.0),
                                PageModeBuilder()
                                    .styleHorizontalPositionTo(1.0)
                                    .styleVerticalPositionTo(7.0)
                                    .actionPrintBarcode(
                                        BarcodeParameter("\${barcode}", BarcodeSymbology.Code128)
                                            .setBarDots(2)
                                            .setHeight(4.0)
                                            .setPrintHri(true)
                                    )
                                    .styleHorizontalPositionTo(37.0)
                                    .styleVerticalPositionTo(6.0)
                                    .actionPrintText(
                                        "\${product_name}",
                                        TextParameter().setWidth(
                                            12,
                                            TextWidthParameter()
                                                .setAlignment(TextAlignment.Center)
                                        )
                                    )
                                    .styleVerticalPositionBy(3.0)
                                    .styleHorizontalPositionTo(37.0)
                                    .actionPrintText(
                                        "$\${price%.2f}\n",
                                        TextParameter()
                                            .setWidth(
                                                12,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Center)
                                            )
                                    )

                                    .styleHorizontalPositionTo(1.0)
                                    .styleVerticalPositionTo(16.5)
                                    .actionPrintBarcode(
                                        BarcodeParameter("\${barcode}", BarcodeSymbology.Code128)
                                            .setBarDots(2)
                                            .setHeight(4.0)
                                            .setPrintHri(true)
                                    )
                                    .styleHorizontalPositionTo(37.0)
                                    .styleVerticalPositionTo(15.5)
                                    .actionPrintText(
                                        "\${product_name}",
                                        TextParameter().setWidth(
                                            12,
                                            TextWidthParameter()
                                                .setAlignment(TextAlignment.Center)
                                        )
                                    )
                                    .styleVerticalPositionBy(3.0)
                                    .styleHorizontalPositionTo(37.0)
                                    .actionPrintText(
                                        "$\${price%.2f}\n",
                                        TextParameter()
                                            .setWidth(
                                                12,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Center)
                                            )
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}