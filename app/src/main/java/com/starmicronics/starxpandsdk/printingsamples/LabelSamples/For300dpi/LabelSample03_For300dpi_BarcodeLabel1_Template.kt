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

class LabelSample03_For300dpi_BarcodeLabel1_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            .addPageMode(
                                PageModeAreaParameter(48.7, 24.0),
                                PageModeBuilder()
                                    .styleHorizontalPositionTo(2.0)
                                    .styleVerticalPositionTo(3.0)
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
                                    .styleVerticalPositionBy(1.0)
                                    .actionPrintText(
                                        "\${product_number}\n"
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(48.7, 10.0)
                                            .setX(0.0)
                                            .setY(14.0),
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(4.0)
                                            .styleVerticalPositionTo(6.0)
                                            .actionPrintBarcode(BarcodeParameter("\${sku}", BarcodeSymbology.UpcA)
                                                .setBarDots(3)
                                                .setHeight(8.0)
                                                .setPrintHri(true)
                                            )
                                            .styleVerticalPositionTo(1.0)
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
                                                    .styleHorizontalPositionTo(31.0)
                                                    .styleMagnification(MagnificationParameter(2,2))
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