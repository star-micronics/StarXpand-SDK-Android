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

class LabelSample08_For203dpi_AllergenWarning_Template {
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
                                    .add(
                                        PageModeBuilder()
                                            .styleMagnification(MagnificationParameter(2,2))
                                            .styleVerticalPositionTo(0.0)
                                            .actionPrintText(
                                                "ALLERGEN WARNING\n",
                                                TextParameter()
                                                    .setWidth(
                                                        24,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .styleHorizontalPositionTo(5.0)
                                            .styleVerticalPositionBy(7.0)
                                            .actionPrintText(
                                                "Item: "
                                            )
                                            .styleUnderLine(true)
                                            .actionPrintText(
                                                "\${item}\n",
                                                TextParameter()
                                                    .setWidth(14)
                                            )
                                            .styleHorizontalPositionTo(5.0)
                                            .styleUnderLine(false)
                                            .styleVerticalPositionBy(5.0)
                                            .actionPrintText(
                                                "Date: "
                                            )
                                            .styleUnderLine(true)
                                            .actionPrintText(
                                                "\${date}\n",
                                                TextParameter()
                                                    .setWidth(14)
                                            )
                                            .styleUnderLine(false)
                                    )
                                    .actionPrintRectangle(PageModeRectangleParameter(2.0, 30.0, 4.0, 4.0))
                                    .actionPrintRectangle(PageModeRectangleParameter(25.0, 30.0, 4.0, 4.0))
                                    .actionPrintRectangle(PageModeRectangleParameter(48.0, 30.0, 4.0, 4.0))
                                    .styleHorizontalPositionTo(3.0)
                                    .styleVerticalPositionTo(31.0)
                                    .actionPrintText(
                                        "\${dairy}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .styleHorizontalPositionBy(4.0)
                                    .actionPrintText(
                                        "Dairy"
                                    )
                                    .styleHorizontalPositionTo(26.0)
                                    .actionPrintText(
                                        "\${fish}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .styleHorizontalPositionBy(4.0)
                                    .actionPrintText(
                                        "Fish"
                                    )
                                    .styleHorizontalPositionTo(49.0)
                                    .actionPrintText(
                                        "\${eggs}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .styleHorizontalPositionBy(4.0)
                                    .actionPrintText(
                                        "Eggs"
                                    )
                                    .actionPrintRectangle(PageModeRectangleParameter(2.0, 36.0, 4.0, 4.0))
                                    .actionPrintRectangle(PageModeRectangleParameter(25.0, 36.0, 4.0, 4.0))
                                    .actionPrintRectangle(PageModeRectangleParameter(48.0, 36.0, 4.0, 4.0))
                                    .styleHorizontalPositionTo(3.0)
                                    .styleVerticalPositionTo(37.0)
                                    .actionPrintText(
                                        "\${peanuts}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .styleHorizontalPositionBy(4.0)
                                    .actionPrintText(
                                        "Peanuts"
                                    )
                                    .styleHorizontalPositionTo(26.0)
                                    .actionPrintText(
                                        "\${shellfish}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .styleHorizontalPositionBy(4.0)
                                    .actionPrintText(
                                        "Shellfish"
                                    )
                                    .styleHorizontalPositionTo(49.0)
                                    .actionPrintText(
                                        "\${soy}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .styleHorizontalPositionBy(4.0)
                                    .actionPrintText(
                                        "Soy"
                                    )
                                    .actionPrintRectangle(PageModeRectangleParameter(2.0, 42.0, 4.0, 4.0))
                                    .actionPrintRectangle(PageModeRectangleParameter(25.0, 42.0, 4.0, 4.0))
                                    .actionPrintRectangle(PageModeRectangleParameter(48.0, 42.0, 4.0, 4.0))
                                    .styleHorizontalPositionTo(3.0)
                                    .styleVerticalPositionTo(43.0)
                                    .actionPrintText(
                                        "\${treenuts}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .styleHorizontalPositionBy(4.0)
                                    .actionPrintText(
                                        "Tree Nuts")

                                    .styleHorizontalPositionTo(26.0)
                                    .actionPrintText(
                                        "\${wheat}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .styleHorizontalPositionBy(4.0)
                                    .actionPrintText(
                                        "Wheat"
                                    )
                                    .styleHorizontalPositionTo(49.0)
                                    .actionPrintText(
                                        "\${gluten}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .styleHorizontalPositionBy(4.0)
                                    .actionPrintText(
                                        "Gluten"
                                    )
                                    .styleHorizontalPositionTo(9.0)
                                    .styleVerticalPositionBy(8.0)
                                    .actionPrintText(
                                        "Other: "
                                    )
                                    .styleUnderLine(true)
                                    .actionPrintText(
                                        "\${other}\n",
                                        TextParameter()
                                            .setWidth(32)
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}