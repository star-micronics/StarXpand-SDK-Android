package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.FontType
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRectangleParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample31_FoodSafetyInfo_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .addPageMode(
                                PageModeAreaParameter(72.0, 58.0),
                                PageModeBuilder()
                                    .actionPrintRectangle(PageModeRectangleParameter(0.0, 0.0, 72.0, 58.0))
                                    .styleFont(FontType.B)
                                    .addPageMode(
                                        PageModeAreaParameter(68.0, 58.0)
                                            .setX(2.0)
                                            .setY(0.0),
                                        PageModeBuilder()
                                            .styleMagnification(MagnificationParameter(3,3))
                                            .styleVerticalPositionTo(6.0)
                                            .actionPrintText(
                                                "FOOD SAFETY INFO\n",
                                                TextParameter()
                                                    .setWidth(
                                                        20,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .styleMagnification(MagnificationParameter(2,2))
                                            .styleVerticalPositionBy(8.0)
                                            .actionPrintText(
                                                "Item:"
                                            )
                                            .styleHorizontalPositionBy(1.0)
                                            .styleUnderLine(true)
                                            .actionPrintText(
                                                "\${item_name}\n",
                                                TextParameter()
                                                    .setWidth(24)
                                            )
                                            .styleUnderLine(false)
                                            .styleVerticalPositionBy(10.0)
                                            .actionPrintText(
                                                "Prepared Date:"
                                            )
                                            .styleHorizontalPositionBy(1.0)
                                            .styleUnderLine(true)
                                            .actionPrintText(
                                                "\${prepared_date}\n",
                                                TextParameter()
                                                    .setWidth(15)
                                            )
                                            .styleUnderLine(false)
                                            .styleVerticalPositionBy(10.0)
                                            .actionPrintText(
                                                "Use By Date:"
                                            )
                                            .styleHorizontalPositionBy(1.0)
                                            .styleUnderLine(true)
                                            .actionPrintText(
                                                "\${use_by_date}\n",
                                                TextParameter()
                                                    .setWidth(17)
                                            )
                                    )
                                    .styleHorizontalPositionTo(3.0)
                                    .styleVerticalPositionTo(21.0)
                                    .actionPrintText(
                                        "Producto\n"
                                    )
                                    .styleHorizontalPositionTo(3.0)
                                    .styleVerticalPositionTo(35.0)
                                    .actionPrintText(
                                        "Fecha de preparación\n"
                                    )
                                    .styleHorizontalPositionTo(3.0)
                                    .styleVerticalPositionTo(49.0)
                                    .actionPrintText(
                                        "Fecha de expiración\n"
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}