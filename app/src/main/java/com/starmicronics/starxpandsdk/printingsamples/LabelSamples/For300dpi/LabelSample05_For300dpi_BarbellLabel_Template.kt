package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.LabelParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample05_For300dpi_BarbellLabel_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(54.0)
                    .addPrinter(
                        PrinterBuilder()
                            .addPageMode(
                                PageModeAreaParameter(55.0, 27.0),
                                PageModeBuilder()
                                    .styleHorizontalPositionTo(6.0)
                                    .styleVerticalPositionTo(8.0)
                                    .actionPrintBarcode(
                                        BarcodeParameter("\${barcode}", BarcodeSymbology.Code128)
                                            .setBarDots(2)
                                            .setHeight(4.0)
                                            .setPrintHri(true)
                                    )
                                    .styleHorizontalPositionTo(35.0)
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
                                    .styleHorizontalPositionTo(35.0)
                                    .actionPrintText(
                                        "$\${price%.2f}\n",
                                        TextParameter()
                                            .setWidth(
                                                12,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Center)
                                            )
                                    )

                                    .styleHorizontalPositionTo(6.0)
                                    .styleVerticalPositionTo(19.0)
                                    .actionPrintBarcode(
                                        BarcodeParameter("\${barcode}", BarcodeSymbology.Code128)
                                            .setBarDots(2)
                                            .setHeight(4.0)
                                            .setPrintHri(true)
                                    )
                                    .styleHorizontalPositionTo(35.0)
                                    .styleVerticalPositionTo(17.0)
                                    .actionPrintText(
                                        "\${product_name}",
                                        TextParameter().setWidth(
                                            12,
                                            TextWidthParameter()
                                                .setAlignment(TextAlignment.Center)
                                        )
                                    )
                                    .styleVerticalPositionBy(3.0)
                                    .styleHorizontalPositionTo(35.0)
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