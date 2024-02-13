package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.CjkCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.InternationalCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModePrintDirection
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRectangleParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthType
import com.starmicronics.starxpandsdk.R

class ReceiptSample11_Cafe_Template {
    companion object {
        fun createReceiptTemplate(context: Context): String {
            val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.receipt_sample16_cafe_template_coffee_cup)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(48.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleCjkCharacterPriority(listOf(CjkCharacterType.Japanese))
                            .styleInternationalCharacter(InternationalCharacterType.Japan)
                            .addPageMode(
                                PageModeAreaParameter(48.0, 120.0),
                                PageModeBuilder()
                                    .actionPrintRectangle(PageModeRectangleParameter(0.0, 0.0, 48.0, 120.0)) // 外枠
                                    .actionPrintRectangle(PageModeRectangleParameter(5.0, 5.0, 16.0, 13.0)) // 収入印紙
                                    .stylePrintDirection(PageModePrintDirection.TopToBottom)
                                    .styleHorizontalPositionTo(7.0)
                                    .styleVerticalPositionTo(30.0)
                                    .actionPrintText(
                                        "収　入"
                                    )
                                    .styleHorizontalPositionTo(7.0)
                                    .styleVerticalPositionTo(37.0)
                                    .actionPrintText(
                                        "印　紙"
                                    )
                                    .styleHorizontalPositionTo(0.0)
                                    .styleVerticalPositionTo(5.0)
                                    .add(
                                        PageModeBuilder()
                                            .styleMagnification(MagnificationParameter(2,2))
                                            .actionPrintText(
                                                "領　収　証",
                                                TextParameter()
                                                    .setWidth(
                                                        19,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                            .setWidthType(TextWidthType.Full)
                                                    )
                                            )
                                    )
                                    .styleHorizontalPositionTo(100.0)
                                    .styleVerticalPositionTo(3.0)
                                    .actionPrintText(
                                        "No.\${number%04d}"
                                    )
                                    .styleHorizontalPositionTo(80.0)
                                    .styleVerticalPositionTo(6.0)
                                    .add(
                                        PageModeBuilder()
                                            .styleMagnification(MagnificationParameter(2,1))
                                            .actionPrintText(
                                                "\${year%04d}"
                                            )
                                            .styleMagnification(MagnificationParameter(1,1))
                                            .actionPrintText(
                                                "年"
                                            )
                                            .styleMagnification(MagnificationParameter(2,1))
                                            .actionPrintText(
                                                "\${month%02d}"
                                            )
                                            .styleMagnification(MagnificationParameter(1,1))
                                            .actionPrintText(
                                                "月"
                                            )
                                            .styleMagnification(MagnificationParameter(2,1))
                                            .actionPrintText(
                                                "\${day%02d}"
                                            )
                                            .styleMagnification(MagnificationParameter(1,1))
                                            .actionPrintText(
                                                "日"
                                            )
                                    )
                                     .styleHorizontalPositionTo(4.0)
                                    .styleVerticalPositionTo(12.0)
                                    .add(
                                        PageModeBuilder()
                                            .styleMagnification(MagnificationParameter(2,2))
                                            .actionPrintText(
                                                "\${name}",
                                                TextParameter()
                                                    .setWidth(
                                                        7,
                                                        TextWidthParameter()
                                                            .setWidthType(TextWidthType.Full)
                                                    )
                                            )
                                            .actionPrintText(
                                                "様"
                                            )
                                            .actionPrintRuledLine(
                                                PageModeRuledLineParameter(4.0, 16.0, 53.0, 16.0)
                                                    .setThickness(0.2)
                                            )
                                    )
                                    .styleHorizontalPositionTo(76.0)
                                    .styleVerticalPositionTo(18.0)
                                    .actionPrintText(
                                        "但し "
                                    )
                                    .actionPrintText(
                                        "\${description}",
                                        TextParameter()
                                            .setWidth(
                                                12,
                                                TextWidthParameter()
                                                    .setWidthType(TextWidthType.Full)
                                            )
                                    )
                                    .styleHorizontalPositionTo(20.0)
                                    .styleVerticalPositionTo(20.0)
                                    .add(
                                        PageModeBuilder()
                                            .styleMagnification(MagnificationParameter(2,2))
                                            .actionPrintText(
                                                "金\${price}円",
                                                TextParameter()
                                                    .setWidth(
                                                        9,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Right)
                                                            .setWidthType(TextWidthType.Full)
                                                    )
                                            )
                                            .actionPrintRuledLine(
                                                PageModeRuledLineParameter(20.0, 24.0, 75.0, 24.0)
                                                    .setThickness(0.2)
                                            )
                                    )
                                    .styleHorizontalPositionTo(24.0)
                                    .styleVerticalPositionTo(26.0)
                                    .actionPrintText(
                                        "上記正に領収いたしました"
                                    )
                                    .styleHorizontalPositionTo(24.0)
                                    .styleVerticalPositionBy(6.0)
                                    .actionPrintText(
                                        "(税抜金額"
                                    )
                                    .actionPrintText(
                                        "\\\${excluding_tax})",
                                        TextParameter()
                                            .setWidth(
                                                15,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right
                                                    )
                                            )
                                    )
                                    .styleHorizontalPositionTo(24.0)
                                    .styleVerticalPositionBy(4.0)
                                    .actionPrintText(
                                        "(消費税等"
                                    )
                                    .actionPrintText(
                                        "\\\${tax})",
                                        TextParameter()
                                            .setWidth(
                                                15,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                                    .actionPrintImage(PageModeImageParameter(logoBitmap,65.0,27.0,80 ))
                                    .addPageMode(
                                        PageModeAreaParameter(8.0, 30.0)
                                            .setX(11.0)
                                            .setY(76.0),
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(0.0)
                                            .actionPrintText(
                                                "\${store_name}\n"
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(10.0, 44.0)
                                            .setX(0.0)
                                            .setY(62.0),
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(0.0)
                                            .actionPrintText(
                                                "\${address}\n",
                                                TextParameter()
                                                    .setWidth(
                                                        29,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                            .actionPrintText(
                                                "TEL \${telephone_number}\n",
                                                TextParameter()
                                                    .setWidth(
                                                        29,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Center)
                                                    )
                                            )
                                    )
                                    .styleHorizontalPositionTo(110.0)
                                    .styleVerticalPositionTo(35.0)
                                    .actionPrintText(
                                        "印"
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}