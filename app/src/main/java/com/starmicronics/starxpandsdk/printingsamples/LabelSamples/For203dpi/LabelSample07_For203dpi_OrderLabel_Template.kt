package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRectangleParameter
import com.starmicronics.stario10.starxpandcommand.printer.CjkCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextPrintType
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample07_For203dpi_OrderLabel_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleCjkCharacterPriority(listOf(CjkCharacterType.Japanese))
                            .addPageMode(
                                PageModeAreaParameter(72.0, 60.0),
                                PageModeBuilder()
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(2.0, 12.0, 40.0, 10.0)
                                            .setThickness(0.5)
                                    ) // 注文番号
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(44.0, 12.0, 26.0, 10.0)
                                            .setThickness(0.5)
                                    ) // 個数
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(2.0, 28.0, 20.0, 10.0)
                                            .setThickness(0.5)
                                    ) // 常温
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(26.0, 28.0, 20.0, 10.0)
                                            .setThickness(0.5)
                                    ) // 冷蔵
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(50.0, 28.0, 20.0, 10.0)
                                            .setThickness(0.5)
                                    ) // 冷凍
                                    .actionPrintRectangle(
                                        PageModeRectangleParameter(2.0, 44.0, 68.0, 15.0)
                                            .setThickness(0.5)
                                    ) // 備考欄
                                    .addPageMode(
                                        PageModeAreaParameter(72.0, 4.0)
                                            .setY(2.0),
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(12.0)
                                            .styleUnderLine(true)
                                            .actionPrintText(
                                                "剥がさずにそのまま配達して下さい\n"
                                            )
                                    )
                                    .styleHorizontalPositionTo(16.0)
                                    .styleVerticalPositionTo(8.0)
                                    .actionPrintText(
                                        "注文番号"
                                    )
                                    .styleHorizontalPositionTo(54.0)
                                    .actionPrintText(
                                        "個数"
                                    )
                                    .styleHorizontalPositionTo(45.0)
                                    .styleVerticalPositionBy(8.0)
                                    .actionPrintText(
                                        "合計"
                                    )
                                    .styleHorizontalPositionBy(15.0)
                                    .actionPrintText(
                                        "袋"
                                    )
                                    .styleHorizontalPositionTo(9.0)
                                    .styleVerticalPositionBy(8.0)
                                    .actionPrintText(
                                        "常温"
                                    )
                                    .styleHorizontalPositionBy(18.0)
                                    .actionPrintText(
                                        "冷蔵"
                                    )
                                    .styleHorizontalPositionBy(18.0)
                                    .actionPrintText(
                                        "冷凍"
                                    )
                                    .styleHorizontalPositionTo(18.0)
                                    .styleVerticalPositionBy(8.0)
                                    .actionPrintText(
                                        "袋"
                                    )
                                    .styleHorizontalPositionBy(21.0)
                                    .actionPrintText(
                                        "袋"
                                    )
                                    .styleHorizontalPositionBy(21.0)
                                    .actionPrintText(
                                        "袋"
                                    )
                                    .styleHorizontalPositionTo(4.0)
                                    .styleVerticalPositionBy(8.0)
                                    .actionPrintText(
                                        "備考欄\${remarks}"
                                    )
                                    .add(
                                        PageModeBuilder()
                                            .styleHorizontalPositionTo(4.0)
                                            .styleMagnification(MagnificationParameter(1,2))
                                            .styleVerticalPositionTo(17.0)
                                            .actionPrintText(
                                                "\${order_number}",
                                                TextParameter()
                                                    .setWidth(
                                                        24,
                                                        TextWidthParameter()
                                                            .setPrintType(TextPrintType.Always)
                                                        )
                                            )
                                            .styleHorizontalPositionBy(12.0)
                                            .actionPrintText(
                                                "\${total_quantity}",
                                                TextParameter()
                                                    .setWidth(
                                                        9,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Right)
                                                            .setPrintType(TextPrintType.Always)
                                                    )
                                            )
                                            .styleHorizontalPositionTo(4.0)
                                            .styleVerticalPositionBy(16.0)
                                            .actionPrintText(
                                                "\${room_temperature}",
                                                TextParameter()
                                                    .setWidth(
                                                        9,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Right)
                                                            .setPrintType(TextPrintType.Always)
                                                    )
                                            )
                                            .styleHorizontalPositionBy(10.0)
                                            .actionPrintText(
                                                "\${refrigeration}",
                                                TextParameter()
                                                    .setWidth(
                                                        9,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Right)
                                                            .setPrintType(TextPrintType.Always)
                                                    )
                                            )
                                            .styleHorizontalPositionBy(11.0)
                                            .actionPrintText(
                                                "\${frozen}",
                                                TextParameter()
                                                    .setWidth(
                                                        9,
                                                        TextWidthParameter()
                                                            .setAlignment(TextAlignment.Right)
                                                            .setPrintType(TextPrintType.Always)
                                                    )
                                            )
                                    )
                                    .addPageMode(
                                        PageModeAreaParameter(64.0, 12.0)
                                            .setX(4.0)
                                            .setY(46.0),
                                        PageModeBuilder()
                                            .actionPrintText(
                                                "\${note}"
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