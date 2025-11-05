package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModePrintDirection
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter
import com.starmicronics.starxpandsdk.R

class LabelSample20_For203dpiAnd300dpi_VisitorLabel_Template {
    companion object {
        fun createReceiptTemplate(context: Context): String {
            val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)
            val userBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.visitor_label_user_profile_picture)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(48.0)
                    .addPrinter(
                        PrinterBuilder()
                            // By setting the base print size of text to x1.5 for 300dpi,
                            // you can print text at the same size as 203dpi.
                            //.styleBaseMagnification(BaseMagnificationParameter().setText(BaseMagnification.X1_5))
                            .addPageMode(
                                PageModeAreaParameter(48.0, 90.0),
                                PageModeBuilder()
                                    .stylePrintDirection(PageModePrintDirection.TopToBottom)
                                    .actionPrintImage(PageModeImageParameter(logoBitmap,7.0,4.0,140 ))
                                    .actionPrintImage(PageModeImageParameter(userBitmap,5.0,14.0,160 ))
                                    .actionPrintRuledLine(PageModeRuledLineParameter(4.0, 12.0, 75.0, 12.0))
                                    .addPageMode(
                                        PageModeAreaParameter(48.0, 52.0)
                                            .setX(0.0)
                                            .setY(28.0),
                                        PageModeBuilder()
                                            .styleBold(true)
                                            .styleHorizontalPositionTo(0.0)
                                            .styleVerticalPositionTo(8.0)
                                            .styleMagnification(MagnificationParameter(3,3))
                                            .actionPrintText(
                                                "VISITOR\n"
                                            )
                                            .styleMagnification(MagnificationParameter(2,2))
                                            .styleVerticalPositionBy(5.0)
                                            .actionPrintText(
                                                "\${name}\n",
                                                TextParameter()
                                                    .setWidth(17)
                                            )
                                            .styleMagnification(MagnificationParameter(1,1))
                                            .styleVerticalPositionBy(1.0)
                                            .actionPrintText(
                                                "\${company_name}\n",
                                                TextParameter()
                                                    .setWidth(34)
                                            )
                                            .styleBold(false)
                                            .styleVerticalPositionBy(1.0)
                                            .actionPrintText(
                                                "Visiting:"
                                            )
                                            .styleBold(true)
                                            .actionPrintText(
                                                "\${visiting}\n",
                                                TextParameter()
                                                    .setWidth(25)
                                            )
                                            .styleBold(false)
                                            .styleVerticalPositionBy(1.0)
                                            .actionPrintText(
                                                "\${datetime}\n",
                                                TextParameter()
                                                    .setWidth(34)
                                            )
                                            .styleHorizontalPositionTo(5.0)
                                            .styleVerticalPositionBy(3.0)
                                            .actionPrintBarcode(
                                                BarcodeParameter("\${barcode}", BarcodeSymbology.Code128)
                                                    .setHeight(5.0)
                                                    .setPrintHri(true)
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(28.0,10.0)
                                            .setX(10.0)
                                            .setY(80.0),
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(0.0)
                                            .styleVerticalPositionTo(0.0)
                                            .stylePrintDirection(PageModePrintDirection.RightToLeft)
                                            .styleBold(true)
                                            .styleLineSpace(0.0)
                                            .actionPrintText(
                                                "FOLD\n",
                                                TextParameter()
                                                    .setWidth(
                                                        18,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .actionPrintText(
                                                "UNDER\n",
                                                TextParameter()
                                                    .setWidth(
                                                        18,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .actionPrintText(
                                                "▼   ▼\n",
                                                TextParameter()
                                                    .setWidth(
                                                        18,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
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