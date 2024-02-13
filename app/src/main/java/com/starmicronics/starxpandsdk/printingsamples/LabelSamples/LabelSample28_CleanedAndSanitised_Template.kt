package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRectangleParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.starxpandsdk.R

class LabelSample28_CleanedAndSanitised_Template {
    companion object {
        fun createLabelTemplate(context: Context): String {
            val handBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.label_sample28_cleanedsanitised_hand)
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .addPageMode(
                                PageModeAreaParameter(72.0, 40.0),
                                PageModeBuilder()
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(0.0, 0.0, 18.0, 18.0)
                                            .setThickness(0.8)
                                    )
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(0.0, 20.0, 72.0, 20.0)
                                            .setThickness(0.8)
                                    )
                                    .actionPrintImage(
                                        PageModeImageParameter(handBitmap, 1.0, 1.0, 130)
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(50.0, 20.0)
                                            .setX(22.0)
                                            .setY(0.0),
                                        PageModeBuilder()
                                            .styleBold(true)
                                            .styleMagnification(MagnificationParameter(2,2))
                                            .styleVerticalPositionTo(3.0)
                                            .actionPrintText(
                                                "CLEANED\n"
                                            )
                                            .styleVerticalPositionBy(4.0)
                                            .actionPrintText(
                                                "& SANITISED\n"
                                            )
                                    )
                                    .styleHorizontalPositionTo(2.0)
                                    .styleVerticalPositionTo(21.0)
                                    .actionPrintText(
                                        "Name:"
                                    )
                                    .styleHorizontalPositionTo(45.0)
                                    .actionPrintText(
                                        "Date:"
                                    )
                                    .styleHorizontalPositionTo(2.0)
                                    .styleMagnification(MagnificationParameter(1,2))
                                    .styleVerticalPositionBy(10.0)
                                    .actionPrintText(
                                        "\${name}",
                                        TextParameter()
                                            .setWidth(28)
                                    )
                                    .actionPrintText(
                                        " "
                                    )
                                    .actionPrintText(
                                        "\${date}",
                                        TextParameter()
                                            .setWidth(16)
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}