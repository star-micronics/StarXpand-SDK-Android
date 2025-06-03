package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnification
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeModel
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeLevel
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.CjkCharacterType

class LabelSample16_For203dpiAnd300dpi_ShelfLabelForInventory_Template {
    companion object {
        fun createShelfLabelForInventory(): String {
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

                            // By setting the base print size of text to x1.5 for 300dpi,
                            // you can print text at the same size as 203dpi.
                            .styleBaseMagnification(BaseMagnificationParameter().setText(BaseMagnification.X1_5))
                            .addPageMode(
                                PageModeAreaParameter(48.0, 36.0),
                                PageModeBuilder()
                                    .styleVerticalPositionTo(6.0)
                                    .add(
                                        PageModeBuilder()
                                            .styleMagnification(MagnificationParameter(2, 2))
                                            .actionPrintText(
                                                "\${name}"
                                            )
                                    )
                                    .styleHorizontalPositionTo(0.0)
                                    .styleVerticalPositionTo(15.0)
                                    .actionPrintQRCode(
                                        QRCodeParameter("\${sku}")
                                            .setCellSize(4)
                                            .setLevel(QRCodeLevel.L)
                                            .setModel(QRCodeModel.Model2)
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(36.0, 24.0)
                                            .setX(12.0)
                                            .setY(12.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "\${company}\n"
                                            )
                                            .actionPrintText(
                                                "\${note}\n"
                                            )
                                            .actionPrintText(
                                                "使用材料名:\${material}\n"
                                            )
                                            .actionPrintText(
                                                "金型取数:\${mold_cavities}\n"
                                            )
                                            .actionPrintText(
                                                "整形場所:\${molding_place}"
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