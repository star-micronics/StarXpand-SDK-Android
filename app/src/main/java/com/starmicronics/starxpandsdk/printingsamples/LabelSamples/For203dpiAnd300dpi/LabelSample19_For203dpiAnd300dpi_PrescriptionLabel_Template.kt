package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.RuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample19_For203dpiAnd300dpi_PrescriptionLabel_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    // Change the printable area setting for this layout according to the printer resolution.
                    // 72.0 for 203dpi, 48.7 for 300dpi
                    .settingPrintableArea(72.0)
                    //.settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            .actionPrintText(
                                "\${contents}\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(1,2))
                                    .actionPrintText(
                                        "Take "
                                    )
                                    .styleBold(true)
                                    .actionPrintText(
                                        "\${number_of_tablet}"
                                    )
                                    .styleBold(false)
                                    .actionPrintText(
                                        " tablet "
                                    )
                                    .styleBold(true)
                                    .actionPrintText(
                                        "\${number_of_times}"
                                    )
                                    .styleBold(false)
                                    .actionPrintText(
                                        " times \${dose_interval}"
                                    )
                            )
                            .actionFeed(7.0)
                            .styleBold(true)
                            .actionPrintText(
                                "Warning "
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "\${warning1}\n" +
                                        "\${warning2}\n" +
                                        "\${warning3}\n"
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "\${name}"
                            )
                            .styleBold(false)
                            .styleHorizontalPositionTo(0.0)
                            .actionPrintText(
                                "\${date}\n",
                                TextParameter()
                                    .setWidth(
                                        42,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.4)
                            )
                            .styleLineSpace(3.0)
                            .actionPrintText(
                                "For Advice \${for_advice}\n" +
                                        "Keep out of sight and reach of children\n" +
                                        "\${address}\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}