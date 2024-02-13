package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter

class LabelSample07_DrinkLabel6_Template {
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
                                    .styleMagnification(MagnificationParameter(1, 2))
                                    .actionPrintText(
                                        "\${order_types}\${order_types_detail}\n"
                                    )
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "#\${order_number%04d}\n"
                                    )
                            )
                            .add(
                                PrinterBuilder()
                                    .styleInvert(true)
                                    .styleMagnification(MagnificationParameter(1, 2))
                                    .actionPrintText(
                                        "\${number}\n" // ?
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
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}