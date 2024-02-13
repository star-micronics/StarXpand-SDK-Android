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

class LabelSample22_ShippingLabel2_Template {
    companion object {
        fun createLabelTemplate(context: Context): String {
            val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)
            val houseBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.label_sample21_shipping_label_house)
            val phoneBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.label_sample21_shipping_label_phones_old)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .addPageMode(
                                PageModeAreaParameter(72.0,72.0),
                                PageModeBuilder()
                                    .stylePrintDirection(PageModePrintDirection.BottomToTop)
                                    .addPageMode(
                                        PageModeAreaParameter(8.0,50.0)
                                            .setY(24.0),
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
                                        PageModeAreaParameter(8.0,42.0)
                                            .setX(10.0)
                                            .setY(24.0),
                                        PageModeBuilder()
                                            .styleLineSpace(0.0)
                                            .actionPrintText(
                                                "\${address}\n"
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(4.0,42.0)
                                            .setX(17.0)
                                            .setY(24.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "\${telephone_number}\n"
                                            )
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(0.0, 22.0, 72.0, 22.0)
                                            .setLineStyle(LineStyle.Single)
                                            .setThickness(1.0)
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
                                    .stylePrintDirection(PageModePrintDirection.LeftToRight)
                                    .addPageMode(
                                        PageModeAreaParameter(47.0, 72.0)
                                            .setX(25.0)
                                            .setY(0.0),
                                        PageModeBuilder()
                                            .styleBold(true)
                                            .styleLineSpace(3.0)
                                            .styleMagnification(
                                                MagnificationParameter(2,2)
                                            )
                                            .styleVerticalPositionTo(6.0)
                                            .actionPrintText(
                                                "TO:\n"
                                            )
                                            .styleBold(false)
                                            .styleVerticalPositionBy(7.0)
                                            .actionPrintText(
                                                "\${name_to}\n"
                                            )
                                            .styleVerticalPositionBy(3.0)
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