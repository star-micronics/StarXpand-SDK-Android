package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter

class LabelSample06_DrinkLabel5_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(48.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .styleLineSpace(1.0)
                            .add(
                                PrinterBuilder()
                                    .styleInvert(true)
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "\${header}\n"
                                    )
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(1, 2))
                                    .actionPrintText(
                                        "\${order_types}\n"
                                    )
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "#\${order_number%04d}\n"
                                    )
                            )
                            .actionPrintText(
                                "\${time}\n"
                            )
                            .actionPrintText(
                                "--------------------------------\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Left)
                                    .styleMagnification(MagnificationParameter(1, 2))
                                    .actionPrintText(
                                        "\${product_name}\n"
                                    )
                                    .add(
                                        PrinterBuilder(
                                            PrinterParameter()
                                                .setTemplateExtension(
                                                    TemplateExtensionParameter()
                                                        .setEnableArrayFieldData(true)
                                                )
                                        )
                                            .styleHorizontalPositionTo(3.0)
                                            .actionPrintText(
                                                "\${item_list.detail}\n"
                                            )
                                    )
                            )
                            .actionPrintText(
                                "--------------------------------\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleInvert(true)
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "\${footer}\n"
                                    )
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}