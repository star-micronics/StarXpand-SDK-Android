package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnification
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter

class LabelSample03_For203dpiAnd300dpi_DrinkLabel2_Template {
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
                            .actionPrintText(
                                "Item: \${item_number%2d} of \${number_of_items}\n"
                            )
                            .actionPrintText(
                                "Items in order: \${items_in_order}"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(1, 2))
                                    .actionFeed(6.0)
                                    .actionPrintText(
                                        "\${customer_name}"
                                    )
                                    .actionFeed(8.0)
                                    .actionPrintText(
                                        "\${product_name}"
                                    )
                                    .actionFeed(6.0)
                            )
                            .add(
                                PrinterBuilder(
                                    PrinterParameter()
                                        .setTemplateExtension(
                                            TemplateExtensionParameter()
                                                .setEnableArrayFieldData(true)
                                        )
                                )
                                    .actionPrintText(
                                        "\${item_list.detail}\n"
                                    )
                            )
                            .actionPrintText(
                                "\nTime: \${time}\n"
                            )
                            .actionPrintText(
                                "\n\${order_types}\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}