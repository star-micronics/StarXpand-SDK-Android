package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnification
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType

class LabelSample02_For203dpiAnd300dpi_DrinkLabel1_Template {
    companion object {
        fun createDrinkLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    // Change the printable area setting for this layout according to the printer resolution.
                    // 48.0 for 203dpi, 48.7 for 300dpi
                    .settingPrintableArea(48.0)
                    // .settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            // By setting the base print size of text to x1.5 for 300dpi,
                            // you can print text at the same size as 203dpi.
                            //.styleBaseMagnification(BaseMagnificationParameter().setText(BaseMagnification.X1_5))
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