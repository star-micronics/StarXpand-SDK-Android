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

class LabelSample04_For203dpi_ShippingLabel1_Template {
    companion object {
        fun createLabelTemplate(context: Context): String {
            val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)
            val houseBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shipping_label_house)
            val phoneBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.shipping_label_phones_old)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleLineSpace(0.0)
                            .addPageMode(
                                PageModeAreaParameter(72.0,21.0),
                                PageModeBuilder()
                                    .addPageMode(
                                        PageModeAreaParameter(50.0,8.0),
                                        PageModeBuilder()
                                            .styleMagnification(
                                                MagnificationParameter(2,2)
                                            )
                                            .styleVerticalPositionTo(4.0)
                                            .actionPrintText(
                                                "\${business_name}\n"
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(42.0,8.0)
                                            .setX(6.0)
                                            .setY(10.0),
                                        PageModeBuilder()
                                            .styleLineSpace(0.0)
                                            .actionPrintText(
                                                "\${address}\n"
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(42.0,4.0)
                                            .setX(6.0)
                                            .setY(17.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "\${telephone_number}\n"
                                            )
                                    )
                                    .actionPrintImage(
                                        PageModeImageParameter(logoBitmap, 47.0, 0.0, 200)
                                    )
                                    .actionPrintImage(
                                        PageModeImageParameter(houseBitmap, 0.0, 10.0, 40)
                                    )
                                    .actionPrintImage(
                                        PageModeImageParameter(phoneBitmap, 0.0, 16.0, 40)
                                    )
                            )
                            .addPageMode(
                                PageModeAreaParameter(72.0, 30.0),
                                PageModeBuilder()
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(0.0, 0.0, 72.0, 30.0)
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(72.0, 3.0)
                                            .setX(1.0)
                                            .setY(1.0),
                                        PageModeBuilder()
                                            .styleBold(true)
                                            .actionPrintText(
                                                "TO:"
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(70.0, 28.0)
                                            .setX(2.0)
                                            .setY(4.0),
                                        PageModeBuilder()
                                            .styleLineSpace(3.0)
                                            .styleMagnification(
                                                MagnificationParameter(2,2)
                                            )
                                            .styleVerticalPositionTo(4.0)
                                            .actionPrintText(
                                                "\${name_to}\n"
                                            )
                                            .styleVerticalPositionBy(4.0)
                                            .actionPrintText(
                                                "\${address_to}\n"
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