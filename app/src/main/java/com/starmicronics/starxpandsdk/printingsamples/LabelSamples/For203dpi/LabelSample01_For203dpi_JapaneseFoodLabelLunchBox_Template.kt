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

class LabelSample01_For203dpi_JapaneseFoodLabelLunchBox_Template {
    companion object {
        fun createJapaneseFoodLabelLunchBox(context: Context): String {
            val plasticBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.japanese_food_label_lunch_box_plastic)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(48.0)
                    .addPrinter(
                        PrinterBuilder()
                            // モデルにより対応する文字エンコーディング指定APIが異なります。
                            // 下記ページのSupported Modelを参照し、ご利用のモデルが対応するAPIを使用してください。
                            // https://star-m.jp/products/s_print/sdk/starxpand/manual/ja/android-kotlin-api-reference/stario10-star-xpand-command/printer-builder/style-cjk-character-priority.html
                            .styleCjkCharacterPriority(listOf(CjkCharacterType.Japanese))
                            // https://star-m.jp/products/s_print/sdk/starxpand/manual/ja/android-kotlin-api-reference/stario10-star-xpand-command/printer-builder/style-second-priority-character-encoding.html
                            //.styleSecondPriorityCharacterEncoding(CharacterEncodingType.Japanese)

                            .addPageMode(
                                PageModeAreaParameter(48.0, 72.0),
                                PageModeBuilder()
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(0.0, 0.0, 48.0, 72.0)
                                            .setCornerRadius(2.0)
                                            .setRoundCorner(true)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(1.0, 10.0, 47.0, 10.0)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(1.0, 27.0, 47.0, 27.0)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(1.0, 44.0, 47.0, 44.0)
                                    )
                                    .styleHorizontalPositionTo(0.0)
                                    .styleVerticalPositionTo(0.0)
                                    .actionPrintText(
                                        "\n"
                                    )
                                    .add(
                                        PageModeBuilder()
                                            .styleMagnification(MagnificationParameter(2, 2))
                                            .actionPrintText(
                                                "\${name}"
                                            )
                                    )
                                    .styleHorizontalPositionTo(1.0)
                                    .styleVerticalPositionBy(8.0)
                                    .actionPrintText(
                                        "消費期限 \${expiry_date}\n"
                                    )
                                    .styleHorizontalPositionBy(2.0)
                                    .styleVerticalPositionBy(3.0)
                                    .actionPrintBarcode(
                                        BarcodeParameter("\${sku}", BarcodeSymbology.Jan13)
                                            .setBarDots(2)
                                            .setHeight(5.0)
                                            .setPrintHri(true)
                                    )
                                    .add(
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(38.0)
                                            .styleMagnification(MagnificationParameter(2, 2))
                                            .styleVerticalPositionTo(18.0)
                                            .actionPrintText(
                                                "\${price}"
                                            )
                                    )
                                    .styleHorizontalPositionTo(32.0)
                                    .styleVerticalPositionTo(23.0)
                                    .actionPrintText(
                                        "お値段(円)"
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(48.0, 20.0)
                                            .setX(1.0)
                                            .setY(28.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "原材料名：\${ingredients}\n"
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(16.0, 14.0)
                                            .setX(1.0)
                                            .setY(46.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "保存方法："
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(32.0, 14.0)
                                            .setX(16.0)
                                            .setY(46.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "\${preservation_method}"
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(48.0, 14.0)
                                            .setX(1.0)
                                            .setY(58.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                        "製造者：\${manufacturer}\n" +
                                                "\${address}\n" +
                                                "TEL \${telephone_number}\n"
                                        )
                                    )
                                    .actionPrintImage(
                                        PageModeImageParameter(plasticBitmap, 40.0, 63.0, 48)
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}