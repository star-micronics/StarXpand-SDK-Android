package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CjkCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class LabelSample14_ExpirationLabelJP {
    companion object {
        fun createExpirationLabelJP(): String {
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

                            .styleAlignment(Alignment.Center)
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "＜要冷蔵＞\n"
                                    )
                            )
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Left)
                                    .actionPrintText(
                                        "10℃以下で保存し、本日中にお召し上がり下さい。\n" +
                                                "　　消費期限\n"
                                    )
                            )
                            .styleAlignment(Alignment.Center)
                            .styleMagnification(MagnificationParameter(2, 2))
                            .actionPrintText(
                                "2010.1.25\n"
                            )
                            .styleMagnification(MagnificationParameter(1, 1))
                            .actionPrintText(
                                "スターショップ\n" +
                                        "静岡　太郎\n" +
                                        "XXX県XXX市XXX町123-1\n" +
                                        "TEL 1234-567-890\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )

            return builder.getCommands()
        }
    }
}