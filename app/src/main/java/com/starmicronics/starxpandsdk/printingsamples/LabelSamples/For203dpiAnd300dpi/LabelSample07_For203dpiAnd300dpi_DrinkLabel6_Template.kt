package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnification
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter

class LabelSample07_For203dpiAnd300dpi_DrinkLabel6_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    // Change the printable area setting for this layout according to the printer resolution.
                    // 48.0 for 203dpi, 48.7 for 300dpi
                    .settingPrintableArea(48.0)
                    //.settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            // By setting the base print size of text to x1.5 for 300dpi,
                            // you can print text at the same size as 203dpi.
                            //.styleBaseMagnification(BaseMagnificationParameter().setText(BaseMagnification.X1_5))
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