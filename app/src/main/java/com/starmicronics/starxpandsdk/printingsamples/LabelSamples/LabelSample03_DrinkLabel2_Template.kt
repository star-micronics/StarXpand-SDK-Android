package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter

class LabelSample03_DrinkLabel2_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(48.0)
                    .addPrinter(
                        PrinterBuilder()
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