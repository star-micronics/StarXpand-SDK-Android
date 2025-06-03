package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnification
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.RuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter

class LabelSample09_For203dpiAnd300dpi_FoodPrepLabel_Template {
    companion object {
        fun createFoodPrepLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    // Change the printable area setting for this layout according to the printer resolution.
                    // 48.0 for 203dpi, 48.7 for 300dpi
                    .settingPrintableArea(48.0)
                    //.settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            // By setting the base print size of text to x1.5 for 300dpi,
                            // you can print text at the same size as 203dpi.
                            //.styleBaseMagnification(BaseMagnificationParameter().setText(BaseMagnification.X1_5))
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Right)
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "\${day_of_week}\n"
                                    )
                            )
                            .styleAlignment(Alignment.Left)
                            .actionPrintText(
                                "Product\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "\${product}\n"
                                    )
                            )
                            .actionPrintText(
                                "Prepared On\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "\${prepared_on}\n"
                                    )
                            )
                            .actionPrintText(
                                "Used by\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "\${used_by}\n"
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(48.0)
                            )
                            .actionPrintText(
                                "User: \${user} ",
                                TextParameter()
                                    .setWidth(16)
                            )
                            .actionPrintText(
                                "Manager: \${manager}\n",
                                TextParameter()
                                    .setWidth(16)
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}