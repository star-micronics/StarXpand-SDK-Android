package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample06_For203dpi_BarcodeLabel1_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .addPageMode(
                                PageModeAreaParameter(72.0, 38.0),
                                PageModeBuilder()
                                    .styleHorizontalPositionTo(2.0)
                                    .styleVerticalPositionTo(6.0)
                                    .add(
                                        PageModeBuilder()
                                            .styleBold(true)
                                            .styleMagnification(MagnificationParameter(2,2))
                                            .actionPrintText(
                                                "\${shop_name}\n"
                                            )
                                    )
                                    .styleHorizontalPositionTo(2.0)
                                    .styleVerticalPositionBy(1.0)
                                    .actionPrintText(
                                        "\${product_name}\n"
                                    )
                                    .styleHorizontalPositionTo(2.0)
                                    .styleVerticalPositionBy(2.0)
                                    .actionPrintText(
                                        "\${product_number}\n"
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(72.0, 16.0)
                                            .setX(0.0)
                                            .setY(20.0),
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(4.0)
                                            .styleVerticalPositionTo(10.0)
                                            .actionPrintBarcode(BarcodeParameter("\${sku}", BarcodeSymbology.UpcA)
                                                .setBarDots(3)
                                                .setHeight(12.0)
                                                .setPrintHri(true)
                                            )
                                            .styleVerticalPositionTo(2.0)
                                            .actionPrintText(
                                                "MSRP $\${msrp%.2f} \n",
                                                TextParameter()
                                                    .setWidth(
                                                        48,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Right)
                                                    )
                                            )
                                            .add(
                                                PageModeBuilder()
                                                    .styleBold(true)
                                                    .styleHorizontalPositionTo(46.0)
                                                    .styleMagnification(MagnificationParameter(2,2))
                                                    .styleVerticalPositionBy(3.0)
                                                    .actionPrintText(
                                                        "$\${selling_price% .2f}\n"
                                                    )
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