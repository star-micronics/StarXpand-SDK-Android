package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.ImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeLevel
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeModel
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeParameter
import com.starmicronics.starxpandsdk.R

class LabelSample01_For203dpiAnd300dpi_TamperProofLabel_Template {
    companion object {
        fun createTamperProofLabel(context: Context): String {
            val checkedBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.tamper_proof_label_checked)

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
                            .styleMagnification(MagnificationParameter(4, 4))
                            .actionPrintText(
                                "SEALED\n"
                            )
                            .actionPrintText(
                                "FRESH\n"
                            )
                            .styleBold(false)
                            .styleMagnification(MagnificationParameter(3, 3))
                            .actionPrintText(
                                "for Safety\n"
                            )
                            .actionPrintImage(
                                ImageParameter(checkedBitmap, 100)
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "................\n"
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "\${remarks}"
                            )
                            .actionPrintQRCode(
                                QRCodeParameter("\${store_url}")
                                    .setCellSize(8)
                                    .setLevel(QRCodeLevel.Q)
                                    .setModel(QRCodeModel.Model2)
                            )
                            .actionCut(CutType.Partial)
                    )
            )

            return builder.getCommands()
        }
    }
}