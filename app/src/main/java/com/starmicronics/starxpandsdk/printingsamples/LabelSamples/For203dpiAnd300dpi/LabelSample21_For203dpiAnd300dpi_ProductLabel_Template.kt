package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PageModeBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnification
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PageModeAreaParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRectangleParameter
import com.starmicronics.stario10.starxpandcommand.printer.PageModeRuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeLevel
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeModel
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeParameter

class LabelSample21_For203dpiAnd300dpi_ProductLabel_Template {
    companion object {
        fun createLabelTemplate(): String {
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
                                PageModeAreaParameter(48.0, 24.0),
                                PageModeBuilder()
                                    .actionPrintRectangle(PageModeRectangleParameter(0.0, 0.0, 48.0, 24.0))
                                    .actionPrintRuledLine(PageModeRuledLineParameter(22.0, 0.0, 22.0, 24.0))
                                    .actionPrintRuledLine(PageModeRuledLineParameter(0.0, 8.0, 22.0, 8.0))
                                    .actionPrintRuledLine(PageModeRuledLineParameter(0.0, 16.0, 22.0, 16.0))
                                    .styleHorizontalPositionTo(2.0)
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .styleVerticalPositionTo(4.0)
                                    .actionPrintText(
                                        "\${code1}\n"
                                    )
                                    .styleHorizontalPositionTo(2.0)
                                    .styleVerticalPositionTo(12.0)
                                    .actionPrintText(
                                        "\${code2}\n"
                                    )
                                    .styleHorizontalPositionTo(2.0)
                                    .styleVerticalPositionTo(20.0)
                                    .actionPrintText(
                                        "\${code3}\n"
                                    )
                                    .styleHorizontalPositionTo(26.0)
                                    .styleVerticalPositionTo(0.0)
                                    .actionPrintQRCode(
                                        QRCodeParameter("\${qrcode}")
                                            .setCellSize(6)
                                            .setLevel(QRCodeLevel.Q)
                                            .setModel(QRCodeModel.Model2)
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}