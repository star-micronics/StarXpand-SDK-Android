package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.*
import com.starmicronics.starxpandsdk.R

class LabelSample02_For300dpi_JapaneseFoodLabelCake_Template {
    companion object {
        fun createJapaneseFoodLabelCake(context: Context): String {
            val plasticBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.japanese_food_label_cake_plastic)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            // モデルにより対応する文字エンコーディング指定APIが異なります。
                            // 下記ページのSupported Modelを参照し、ご利用のモデルが対応するAPIを使用してください。
                            // https://star-m.jp/products/s_print/sdk/starxpand/manual/ja/android-kotlin-api-reference/stario10-star-xpand-command/printer-builder/style-cjk-character-priority.html
                            .styleCjkCharacterPriority(listOf(CjkCharacterType.Japanese))
                            // https://star-m.jp/products/s_print/sdk/starxpand/manual/ja/android-kotlin-api-reference/stario10-star-xpand-command/printer-builder/style-second-priority-character-encoding.html
                            //.styleSecondPriorityCharacterEncoding(CharacterEncodingType.Japanese)

                            .addPageMode(
                                PageModeAreaParameter(48.7, 33.3),
                                PageModeBuilder()
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(0.0, 2.3, 41.0, 31.0)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(0.0, 5.0, 41.0, 5.0)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(0.0, 13.5, 41.0, 13.5)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(0.0, 16.2, 41.0, 16.2)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(0.0, 18.9, 41.0, 18.9)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(0.0, 24.5, 41.0, 24.5)
                                    )
                                    .actionPrintRuledLine(
                                        PageModeRuledLineParameter(12.5, 2.3, 12.5, 33.3)
                                    )
                                    .styleHorizontalPositionTo(0.5)
                                    .styleVerticalPositionTo(0.0)
                                    .actionPrintText(
                                        "品　名"
                                    )
                                    .styleHorizontalPositionTo(20.0)
                                    .actionPrintText(
                                        "\${name}\n"
                                    )
                                    .styleHorizontalPositionTo(0.5)
                                    .actionPrintText(
                                        "名　称"
                                    )
                                    .styleHorizontalPositionTo(23.0)
                                    .actionPrintText(
                                        "\${classification}\n"
                                    )
                                    .styleHorizontalPositionTo(0.5)
                                    .actionPrintText(
                                        "原材料名"
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(26.0, 16.0)
                                            .setX(13.0)
                                            .setY(5.5),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "\${ingredients}\n"
                                            )
                                    )
                                    .styleHorizontalPositionTo(0.5)
                                    .styleVerticalPositionTo(14.0)
                                    .actionPrintText(
                                        "内容量"
                                    )
                                    .styleHorizontalPositionTo(13.0)
                                    .actionPrintText(
                                        "\${contents}\n"
                                    )
                                    .styleHorizontalPositionTo(0.5)
                                    .actionPrintText(
                                        "消費期限"
                                    )
                                    .styleHorizontalPositionTo(13.0)
                                    .actionPrintText(
                                        "\${expiry_date}\n"
                                    )
                                    .styleHorizontalPositionTo(0.5)
                                    .actionPrintText(
                                        "保存方法"
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(26.0, 12.0)
                                            .setX(13.0)
                                            .setY(19.5),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "\${preservation_method}"
                                            )
                                    )
                                    .styleHorizontalPositionTo(0.5)
                                    .styleVerticalPositionTo(25.0)
                                    .actionPrintText(
                                        "製造者"
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(26.0, 12.0)
                                            .setX(13.0)
                                            .setY(25.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "\${manufacturer}"
                                            )
                                    )
                                    .actionPrintImage(
                                        PageModeImageParameter(plasticBitmap, 42.0, 0.0, 48)
                                    )
                                    .stylePrintDirection(PageModePrintDirection.BottomToTop)
                                    .styleHorizontalPositionTo(2.0)
                                    .styleVerticalPositionTo(43.0)
                                    .actionPrintBarcode(
                                        BarcodeParameter("\${sku}", BarcodeSymbology.Jan13)
                                            .setBarDots(3)
                                            .setHeight(3.0)
                                            .setPrintHri(true)
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}