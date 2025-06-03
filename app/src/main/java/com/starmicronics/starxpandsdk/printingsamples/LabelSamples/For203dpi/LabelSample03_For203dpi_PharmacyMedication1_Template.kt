package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.*
import com.starmicronics.starxpandsdk.R

class LabelSample03_For203dpi_PharmacyMedication1_Template {
    companion object {
        fun createLabelTemplate(context: Context): String {
            val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.pharmacy_medication1_logo)
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .addPageMode(
                                PageModeAreaParameter(72.0, 75.0),
                                PageModeBuilder()
                                    .actionPrintImage(
                                        PageModeImageParameter(logoBitmap, 0.0, 0.0, 130)
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(54.0, 20.0 )
                                            .setX(18.0)
                                            .setY(0.0),
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(0.0)
                                            .styleVerticalPositionTo(4.0)
                                            .styleMagnification(
                                                MagnificationParameter(2,2)
                                            )
                                            .actionPrintText(
                                                "\${store_name}\n"
                                            )
                                            .styleMagnification(
                                                MagnificationParameter(1,1)
                                            )
                                            .actionPrintText(
                                                "\${address}\n"
                                            )
                                            .actionPrintText(
                                                "\${telephone_number}\n"
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(72.0, 20.0)
                                            .setY(20.0),
                                        PageModeBuilder()
                                            .actionPrintRectangle(
                                                PageModeRectangleParameter(0.0, 0.0, 72.0, 16.0)
                                            )
                                            .styleHorizontalPositionTo(1.0)
                                            .styleMagnification(
                                                MagnificationParameter(2,2)
                                            )
                                            .styleVerticalPositionTo(4.0)
                                            .actionPrintText(
                                                "\${number}\n"
                                            )
                                            .styleHorizontalPositionTo(1.0)
                                            .styleVerticalPositionBy(4.0)
                                            .actionPrintText(
                                                "\${customer_name}"
                                            )
                                    )
                                    .styleVerticalPositionTo(40.0)
                                    .actionPrintText(
                                        "Usage:\n"
                                    )
                                    .styleBold(true)
                                    .actionPrintText(
                                        "\${usage}\n"
                                    )
                                    .styleBold(false)
                                    .addPageMode(
                                        PageModeAreaParameter(72.0, 10.0)
                                            .setY(55.0),
                                        PageModeBuilder()
                                            .actionPrintRectangle(
                                                PageModeRectangleParameter(0.0, 0.0, 72.0, 8.0)
                                            )
                                            .styleHorizontalPositionTo(1.0)
                                            .styleMagnification(
                                                MagnificationParameter(2,2)
                                            )
                                            .styleVerticalPositionTo(4.0)
                                            .actionPrintText(
                                                "□ Refill\t□ No Refill\n"
                                            )
                                    )
                                    .styleVerticalPositionTo(65.0)
                                    .actionPrintText(
                                        "EXP: \${expiry_date}\n"
                                    )
                                    .add(
                                        PageModeBuilder()
                                            .styleMagnification(
                                                MagnificationParameter(2,1)
                                                )
                                            .actionPrintText(
                                                "Quantity: \${quantity}\n"
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