package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRectangleParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample30_ExpirationDate_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .addPageMode(
                                PageModeAreaParameter(72.0, 35.0),
                                PageModeBuilder()
                                    .actionPrintRectangle(PageModeRectangleParameter(0.0, 0.0, 72.0, 35.0))
                                    .styleMagnification(MagnificationParameter(3,3))
                                    .styleVerticalPositionTo(5.0)
                                    .actionPrintText(
                                        "Expiration Date:\n"
                                    )
                                    .styleHorizontalPositionTo(2.0)
                                    .styleVerticalPositionBy(10.0)
                                    .styleUnderLine(true)
                                    .actionPrintText(
                                        "\${expiration_date}\n",
                                        TextParameter()
                                            .setWidth(
                                                15,
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