package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.InternationalCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextEllipsizeType
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample10_For203dpiAnd300dpi_FoodDeliveryLabel_Template {
    companion object {
        fun createFoodDeliveryLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    // Change the printable area setting for this layout according to the printer resolution.
                    // 72.0 for 203dpi, 48.7 for 300dpi
                    .settingPrintableArea(72.0)
                    //.settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            .styleInternationalCharacter(InternationalCharacterType.Usa)
                            .styleCharacterSpace(0.0)
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(3, 3))
                                    .actionPrintText(
                                        "\${store_name}\n"
                                    )
                                    .add(
                                        PrinterBuilder()
                                            .styleInvert(true)
                                            .actionPrintText(
                                                "\${order_name}\n"
                                            )
                                    )
                            )
                            .styleAlignment(Alignment.Center)
                            .actionPrintText(
                                "Placed at \${time}\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleUnderLine(true)
                                    .actionPrintText(
                                        "                                                \n"
                                    )
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(2, 2))
                                    .actionPrintText(
                                        "\${order_types}\n"
                                    )
                            )
                            .add(
                                PrinterBuilder()
                                    .styleUnderLine(true)
                                    .actionPrintText(
                                        "                                                \n"
                                    )
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
                                        "\${item_list.name}",
                                        TextParameter().setWidth(
                                            40,
                                            TextWidthParameter()
                                                .setEllipsizeType(TextEllipsizeType.End)
                                        )
                                    )
                                    .actionPrintText(
                                        "$\${item_list.price%.2f}\n",
                                        TextParameter()
                                            .setWidth(
                                                8,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                            )
                            .actionPrintText(
                                "------------------------------------------------\n"
                            )
                            .actionPrintText(
                                "Subtotal"
                            )
                            .actionPrintText(
                                "$\${subtotal%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        40,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right))
                            )
                            .actionPrintText(
                                "Amount paid"
                            )
                            .actionPrintText(
                                "$\${amount_paid%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        37,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right))
                            )
                            .actionPrintText(
                                "item \${item_count}",
                                TextParameter()
                                    .setWidth(10)
                            )
                            .actionPrintText(
                                "$\${item_price%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        38,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right))
                            )
                            .styleAlignment(Alignment.Left)
                            .actionPrintText(
                                "------------------------------------------------\n" +
                                        "\${note}\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}