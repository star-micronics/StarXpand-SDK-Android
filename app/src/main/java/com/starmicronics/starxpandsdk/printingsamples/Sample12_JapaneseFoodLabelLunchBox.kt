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

class Sample12_JapaneseFoodLabelLunchBox {
    companion object {
        fun createJapaneseFoodLabelLunchBox(context: Context): String {
            val plasticBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.sample12_japanese_food_label_lunch_box_plastic)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
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
                                            .setRoundCorner(true)
                                            .setCornerRadius(2.0)
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
                                                " 唐 揚 げ 弁 当 "
                                            )
                                    )
                                    .styleHorizontalPositionTo(1.0)
                                    .styleVerticalPositionBy(8.0)
                                    .actionPrintText(
                                        "消費期限 12. 9. 8   22時\n"
                                    )
                                    .styleHorizontalPositionBy(2.0)
                                    .styleVerticalPositionBy(3.0)
                                    .actionPrintBarcode(
                                        BarcodeParameter("2100100114008", BarcodeSymbology.Jan13)
                                            .setBarDots(2)
                                            .setHeight(5.0)
                                            .setPrintHri(true)
                                    )
                                    .add(
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(38.0)
                                            .styleVerticalPositionTo(18.0)
                                            .styleMagnification(MagnificationParameter(2, 2))
                                            .actionPrintText(
                                                "400"
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
                                                "原材料名：ご飯(国内産)、鶏肉、おかず、調味料(アミノ酸等)、着色料(赤102、106、黄4)、(原材料の一部に小麦、大豆を含む)\n"
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(48.0, 28.0)
                                            .setX(1.0)
                                            .setY(46.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                        "保存方法：直射日光・高温多湿を\n" +
                                        "　　　　　避けお早めにお召上が\n" +
                                        "　　　　　りください。\n" +
                                        "製造者：スターショップ\n" +
                                        "XXX県XXX市XXX町123-1\n" +
                                        "TEL 1234-567-890\n"
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