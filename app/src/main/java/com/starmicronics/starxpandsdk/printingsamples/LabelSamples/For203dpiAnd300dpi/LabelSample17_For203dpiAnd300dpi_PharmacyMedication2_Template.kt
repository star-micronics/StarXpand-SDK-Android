package com.starmicronics.starxpandsdk.printingsamples

import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.CjkCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthType

class LabelSample17_For203dpiAnd300dpi_PharmacyMedication2_Template {
    companion object {
        fun createLabelTemplate(): String {
            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    // Change the printable area setting for this layout according to the printer resolution.
                    // 72.0 for 203dpi, 48.7 for 300dpi
                    .settingPrintableArea(72.0)
                    //.settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            .styleCjkCharacterPriority(listOf(CjkCharacterType.Japanese))
                            .actionPrintText(
                                "\${patient_name} 様",
                                TextParameter()
                                    .setWidth(24)
                            )
                            .actionPrintText(
                                "処方日 \${prescription_date}  \n",
                                TextParameter()
                                    .setWidth(
                                        24,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintText(
                                "調剤日 \${dispensing_date}  \n",
                                TextParameter()
                                    .setWidth(
                                        48,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionFeedLine(1)
                            .actionPrintText(
                                "\${hospital_name}:\n"
                            )
                            .actionFeedLine(1)
                            .add(
                                PrinterBuilder(
                                    PrinterParameter()
                                        .setTemplateExtension(
                                            TemplateExtensionParameter()
                                                .setEnableArrayFieldData(true)
                                        )
                                )
                                    .actionPrintText(
                                        "  \${item_list1.name}",
                                         TextParameter()
                                             .setWidth(
                                                 17,
                                                 TextWidthParameter()
                                                     .setWidthType(TextWidthType.Full)
                                             )
                                    )
                                    .actionPrintText(
                                        "\${item_list1.take_the_medicine}",
                                        TextParameter()
                                            .setWidth(5)
                                    )
                                    .actionPrintText(
                                        "\${item_list1.dosage}\n",
                                        TextParameter()
                                            .setWidth(9)
                                    )
                            )
                            .actionPrintText(
                                "  ----------------------------------------------\n"
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
                                        "  \${item_list2.name}",
                                        TextParameter()
                                            .setWidth(
                                                17,
                                                TextWidthParameter()
                                                    .setWidthType(TextWidthType.Full)
                                            )
                                    )
                                    .actionPrintText(
                                        "\${item_list2.take_the_medicine}",
                                        TextParameter()
                                            .setWidth(5)
                                    )
                                    .actionPrintText(
                                        "\${item_list2.dosage}\n",
                                        TextParameter()
                                            .setWidth(9)
                                    )
                                    .actionPrintText(
                                        "　\${item_list2.note}\n"
                                    )
                            )
                            .actionPrintText(
                                "  ----------------------------------------------\n"
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
                                        "  \${item_list3.name}",
                                        TextParameter()
                                            .setWidth(
                                                17,
                                                TextWidthParameter()
                                                    .setWidthType(TextWidthType.Full)
                                            )
                                    )
                                    .actionPrintText(
                                        "\${item_list3.take_the_medicine}",
                                        TextParameter()
                                            .setWidth(5)
                                    )
                                    .actionPrintText(
                                        "\${item_list3.dosage}\n",
                                        TextParameter()
                                            .setWidth(9)
                                    )
                                    .actionPrintText(
                                        "　\${item_list3.note}\n"
                                    )
                            )
                            .actionPrintText(
                                "  ----------------------------------------------\n"
                            )
                            .actionPrintText(
                                "\${store_name}  TEL \${telephone_number}\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}