package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModePrintDirection
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRectangleParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample10_For203dpi_CoffeeOrder_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .addPageMode(
                                PageModeAreaParameter(72.0, 80.0),
                                PageModeBuilder()
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(20.0, 3.0, 10.0, 10.0)
                                            .setThickness(0.4)
                                    )
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(50.0, 3.0, 10.0, 10.0)
                                            .setThickness(0.4)
                                    )
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(20.0, 18.0, 10.0, 10.0)
                                            .setThickness(0.4)
                                    )
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(50.0, 18.0, 10.0, 10.0)
                                            .setThickness(0.4)
                                    )
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(20.0, 31.0, 40.0, 10.0)
                                            .setThickness(0.4)
                                    )
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(20.0, 45.0, 40.0, 10.0)
                                            .setThickness(0.4)
                                    )
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(20.0, 59.0, 10.0, 6.0)
                                            .setThickness(0.4)
                                    )
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(44.0, 59.0, 16.0, 6.0)
                                            .setThickness(0.4)
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(12.0, 80.0)
                                            .setX(0.0)
                                            .setY(0.0),
                                        PageModeBuilder()
                                            .styleBold(true)
                                            .styleMagnification(MagnificationParameter(2,2))
                                            .stylePrintDirection(PageModePrintDirection.BottomToTop)
                                            .styleVerticalPositionTo(6.0)
                                            .actionPrintText(
                                                "\${store_name}\n",
                                                TextParameter()
                                                    .setWidth(
                                                        24,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Right)
                                                )
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(60.0,80.0)
                                            .setX(12.0)
                                            .setY(0.0),
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(8.0)
                                            .styleVerticalPositionTo(0.0)
                                            .actionPrintText(
                                                "Decaf",
                                                TextParameter()
                                                    .setWidth(
                                                        6,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .styleHorizontalPositionTo(38.0)
                                            .actionPrintText(
                                                "Shots\n",
                                                TextParameter()
                                                    .setWidth(
                                                        6,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .add(
                                                PageModeBuilder()
                                                    .styleMagnification(MagnificationParameter(2,2))
                                                    .styleHorizontalPositionTo(8.0)
                                                    .styleVerticalPositionBy(4.0)
                                                    .actionPrintText(
                                                        "\${decaf}",
                                                        TextParameter()
                                                            .setWidth(
                                                                3,
                                                                TextWidthParameter()
                                                                    .setAlignment(TextAlignment.Center)
                                                            )
                                                    )
                                                    .styleHorizontalPositionTo(38.0)
                                                    .actionPrintText(
                                                        "\${shots}\n",
                                                        TextParameter()
                                                            .setWidth(
                                                                3,
                                                                TextWidthParameter()
                                                                    .setAlignment(TextAlignment.Center)
                                                            )
                                                    )
                                            )
                                            .styleHorizontalPositionTo(8.0)
                                            .styleVerticalPositionTo(15.0)
                                            .actionPrintText(
                                                "Size",
                                                TextParameter()
                                                    .setWidth(
                                                        6,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .styleHorizontalPositionTo(38.0)
                                            .actionPrintText(
                                                "Milk\n",
                                                TextParameter()
                                                    .setWidth(
                                                        6,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .add(
                                                PageModeBuilder()
                                                    .styleMagnification(MagnificationParameter(2,2))
                                                    .styleHorizontalPositionTo(8.0)
                                                    .styleVerticalPositionBy(4.0)
                                                    .actionPrintText(
                                                        "\${size}",
                                                        TextParameter()
                                                            .setWidth(
                                                                3,
                                                                TextWidthParameter()
                                                                    .setAlignment(TextAlignment.Center)
                                                            )
                                                    )
                                                    .styleHorizontalPositionTo(38.0)
                                                    .actionPrintText(
                                                        "\${milk}\n",
                                                        TextParameter()
                                                            .setWidth(
                                                                3,
                                                                TextWidthParameter()
                                                                    .setAlignment(TextAlignment.Center)
                                                            )
                                                    )
                                            )
                                            .styleHorizontalPositionTo(8.0)
                                            .styleVerticalPositionTo(28.0)
                                            .actionPrintText(
                                                "Syrup\n",
                                                TextParameter()
                                                    .setWidth(
                                                        27,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .add(
                                                PageModeBuilder()
                                                    .styleMagnification(MagnificationParameter(2,2))
                                                    .styleHorizontalPositionTo(8.0)
                                                    .styleVerticalPositionBy(4.0)
                                                    .actionPrintText(
                                                        "\${syrup}\n",
                                                        TextParameter()
                                                            .setWidth(
                                                                13,
                                                                TextWidthParameter()
                                                                    .setAlignment(TextAlignment.Center)
                                                            )
                                                    )
                                            )
                                            .styleHorizontalPositionTo(8.0)
                                            .styleVerticalPositionTo(42.0)
                                            .actionPrintText(
                                                "Custom\n",
                                                TextParameter()
                                                    .setWidth(
                                                        27,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .add(
                                                PageModeBuilder()
                                                    .styleMagnification(MagnificationParameter(2,2))
                                                    .styleHorizontalPositionTo(8.0)
                                                    .styleVerticalPositionBy(4.0)
                                                    .actionPrintText(
                                                        "\${custom}\n",
                                                        TextParameter()
                                                            .setWidth(
                                                                13,
                                                                TextWidthParameter()
                                                                    .setAlignment(TextAlignment.Center)
                                                            )
                                                    )
                                            )
                                            .styleHorizontalPositionTo(8.0)
                                            .styleVerticalPositionTo(56.0)
                                            .actionPrintText(
                                                "Drink",
                                                TextParameter()
                                                    .setWidth(
                                                        6,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .styleHorizontalPositionTo(32.0)
                                            .actionPrintText(
                                                "Iced\n",
                                                TextParameter()
                                                    .setWidth(
                                                        10,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .add(
                                                PageModeBuilder()
                                                    .styleHorizontalPositionTo(8.0)
                                                    .styleVerticalPositionBy(1.0)
                                                    .actionPrintText(
                                                        "\${drink}",
                                                        TextParameter()
                                                            .setWidth(
                                                                6,
                                                                TextWidthParameter()
                                                                    .setAlignment(TextAlignment.Center)
                                                            )
                                                    )
                                                    .styleHorizontalPositionTo(32.0)
                                                    .actionPrintText(
                                                        "\${iced}\n",
                                                        TextParameter()
                                                            .setWidth(
                                                                10,
                                                                TextWidthParameter()
                                                                    .setAlignment(TextAlignment.Center)
                                                            )
                                                    )
                                            )
                                            .add(
                                                PageModeBuilder()
                                                    .styleMagnification(MagnificationParameter(2,2))
                                                    .styleVerticalPositionBy(3.0)
                                                    .actionPrintText(
                                                        "Name:"
                                                    )
                                                    .styleHorizontalPositionBy(1.0)
                                                    .actionPrintText(
                                                        "\${customer_name}\n",
                                                        TextParameter()
                                                            .setWidth(14)
                                                    )
                                            )
                                            .actionPrintText(
                                                "FOR HERE"
                                            )
                                            .actionPrintText(
                                                "\${item_number} of \${total_items}",
                                                TextParameter()
                                                    .setWidth(
                                                        32,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Right)
                                                    )
                                            )
                                            .actionPrintText(
                                                "\${order_number}",
                                                TextParameter()
                                                    .setWidth(30)
                                            )
                                            .actionPrintText(
                                                "\${time}",
                                                TextParameter()
                                                    .setWidth(
                                                        10,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Right)
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