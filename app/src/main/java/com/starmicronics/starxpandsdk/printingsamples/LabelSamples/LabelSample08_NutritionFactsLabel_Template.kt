package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.RuledLineParameter
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter

class LabelSample08_NutritionFactsLabel_Template {
    companion object {
        fun createNutritionFactsLabel(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Center)
                                    .styleMagnification(MagnificationParameter(3, 3))
                                    .actionPrintText(
                                        "Nutrition Facts\n"
                                    )
                            )
                            .styleAlignment(Alignment.Left)
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(0.1)
                            )
                            .actionPrintText(
                                "\${servings_per_container} servings per container\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .actionPrintText(
                                        "Serving size"
                                    )
                                    .actionPrintText(
                                        "\${serving_size}\n",
                                        TextParameter()
                                            .setWidth(
                                                36,
                                                TextWidthParameter().setAlignment(TextAlignment.Right)
                                            )
                                    )
                                    .actionPrintRuledLine(
                                        RuledLineParameter(72.0)
                                            .setThickness(4.0)
                                    )
                                    .actionPrintText(
                                        "Amount per serving\n"
                                    )
                                    .styleMagnification(MagnificationParameter(3, 3))
                                    .actionPrintText(
                                        "\${amount_per_serving}\n"
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(2.0)
                            )
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Right)
                                    .styleBold(true)
                                    .actionPrintText(
                                        "% Daily Value*\n"
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
                                    .actionPrintRuledLine(
                                        RuledLineParameter(72.0)
                                            .setThickness(0.1)
                                    )
                                    .actionPrintText(
                                    "\${item1_list.name} \${item1_list.amount}",
                                        TextParameter()
                                            .setWidth(44)
                                    )
                                    .actionPrintText(
                                        "\${item1_list.percentage}\n",
                                        TextParameter()
                                            .setWidth(
                                                4,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                        )
                                    )
                            )
                            .actionPrintRuledLine(
                                RuledLineParameter(72.0)
                                    .setThickness(4.0)
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
                                        "\${item2_list.name} \${item2_list.amount}",
                                        TextParameter()
                                            .setWidth(44)
                                    )
                                    .actionPrintText(
                                        "\${item2_list.percentage}\n",
                                        TextParameter().setWidth(
                                            4,
                                            TextWidthParameter().setAlignment(TextAlignment.Right)
                                        )
                                    )
                                    .actionPrintRuledLine(
                                        RuledLineParameter(72.0)
                                            .setThickness(0.1)
                                    )
                            )
                            .actionPrintText(
                                "* The % Daily Value (DV) tells you how much a nutrient in a serving of food contributes to a daily diet. 2,000 calories a day is used for general nutrition advice.\n" +
                                        "↑ One Serving adds 17g of sugar to your diet and represents 34% of the daily value for addded sugars.\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}