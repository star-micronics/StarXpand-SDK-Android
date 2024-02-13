package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class LabelSample02_DrinkLabel1_Template {
    companion object {
        fun createDrinkLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(48.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleBold(true)
                            .actionPrintText(
                                "Item:\t\${item_number} of \${number_of_items}\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleMagnification(MagnificationParameter(1, 2))
                                    .actionPrintText(
                                        "\${customer_name}\n\${item_name}\n"
                                    )
                            )
                            .actionPrintText(
                                "\${order_detail}\n" +
                                        "\n" +
                                        "Time:\t\${time}\n" +
                                        "Reg:\t\${register}\n" +
                                        "\n" +
                                        "--------------------------------\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .actionPrintText(
                                        "\${note}\n"
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