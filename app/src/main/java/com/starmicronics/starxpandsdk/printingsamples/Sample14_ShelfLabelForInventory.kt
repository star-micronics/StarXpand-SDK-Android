package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeModel
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeLevel
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.CharacterEncodingType
import com.starmicronics.stario10.starxpandcommand.printer.CjkCharacterType

class Sample14_ShelfLabelForInventory {
    companion object {
        fun createShelfLabelForInventory(): String {
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
                                PageModeAreaParameter(48.0, 36.0),
                                PageModeBuilder()
                                    .styleVerticalPositionTo(6.0)
                                    .add(
                                        PageModeBuilder()
                                            .styleMagnification(MagnificationParameter(2, 2))
                                            .actionPrintText(
                                                "1234 5678 素材Ｓ"
                                            )
                                    )
                                    .styleHorizontalPositionTo(0.0)
                                    .styleVerticalPositionTo(15.0)
                                    .actionPrintQRCode(
                                        QRCodeParameter("12345678素材Ｓ")
                                            .setCellSize(4)
                                            .setLevel(QRCodeLevel.L)
                                            .setModel(QRCodeModel.Model2)
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(35.0, 24.0)
                                            .setX(13.0)
                                            .setY(12.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "スター精密\n" +
                                                        "S1\n" +
                                                        "使用材料名:アクリル樹脂\n" +
                                                        "金型取数:1\n" +
                                                        "整形場所:Star Micronics\n"
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