package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter
import com.starmicronics.starxpandsdk.R

class LabelSample04_For300dpi_Sale50PercentOff_Template {
    companion object {
        fun createLabelTemplate(context: Context): String {
            val backgroundBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sale_50off_background)
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .addPageMode(
                                PageModeAreaParameter(48.7, 46.0),
                                PageModeBuilder()
                                    .actionPrintImage(PageModeImageParameter(backgroundBitmap, 0.0, 0.0, 576))
                                    .styleHorizontalPositionTo(4.0)
                                    .styleVerticalPositionTo(27.0)
                                    .styleMagnification(MagnificationParameter(4,4))
                                    .actionPrintText(
                                        "\${note}\n",
                                        TextParameter()
                                            .setWidth(
                                                10,
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