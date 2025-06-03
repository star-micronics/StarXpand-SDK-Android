package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class LabelSample11_For203dpiAnd300dpi_ShippingAddressLabel_Template {
    companion object {
        fun createShippingAddressLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    // Change the printable area setting for this layout according to the printer resolution.
                    // 72.0 for 203dpi, 48.7 for 300dpi
                    .settingPrintableArea(72.0)
                    //.settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .styleMagnification(MagnificationParameter(2, 2))
                            .actionPrintText(
                                "\${name}\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .actionPrintText(
                                        "\${company}\n"
                                    )
                            )
                            .actionPrintText(
                                "\${address}\n"
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "\${country}\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}