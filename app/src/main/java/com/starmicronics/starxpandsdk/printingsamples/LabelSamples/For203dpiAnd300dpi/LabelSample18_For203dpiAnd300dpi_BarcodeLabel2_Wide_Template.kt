package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class LabelSample18_For203dpiAnd300dpi_BarcodeLabel2_Wide_Template {
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
                            .styleAlignment(Alignment.Center)
                            .styleBold(true)
                            .actionPrintText(
                                "\${product_name}\n"
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "\${parameter1} / \${parameter2} / \${parameter3} / \${parameter4}\n"
                            )
                            .actionPrintBarcode(
                                BarcodeParameter("\${sku}", BarcodeSymbology.UpcA)
                                    .setBarDots(3)
                                    .setHeight(12.0)
                                    .setPrintHri(true)
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}