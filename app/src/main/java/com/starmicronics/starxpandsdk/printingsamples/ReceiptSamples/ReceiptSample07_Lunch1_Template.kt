package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CjkCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.ImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.InternationalCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthType
import com.starmicronics.starxpandsdk.R

class ReceiptSample07_Lunch1_Template {
    companion object {
        fun createReceiptTemplate(context: Context): String {
            val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(48.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .styleCjkCharacterPriority(listOf(CjkCharacterType.Japanese))
                            .styleInternationalCharacter(InternationalCharacterType.Japan)
                            .actionPrintImage(
                                ImageParameter(logoBitmap, 400)
                            )
                            .actionFeedLine(1)
                            .actionPrintText(
                                "["
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "領収書"
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "]\n"
                            )
                            .actionPrintText(
                                "\${store_name}\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleLineSpace(3.00)
                                    .actionPrintText(
                                        "\${address}"
                                    )
                            )
                            .actionPrintText(
                                        "\nTEL:\${telephone_number}\n" +
                                        "登録番号:\${registration_number}\n"
                            )
                            .styleAlignment(Alignment.Left)
                            .actionPrintText(
                                "\${datetime}\n" +
                                        "レジ:\${register_number%04d}" + "    担当:\${staff_number%04d}\n" +
                                        "人数:\${number_of_people}名\n" +
                                        "伝票名:\${voucher_name}\n" +
                                        "取引No:\${transaction_number}\n"
                            )
                            .actionFeedLine(1)
                            .actionPrintText(
                                "ご利用ありがとうございます\n"
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
                                        "\${item_list.name}\n",
                                        TextParameter()
                                            .setWidth(
                                                16,
                                                TextWidthParameter()
                                                    .setWidthType(TextWidthType.Full)
                                            )
                                    )
                                    .actionPrintText(
                                        "\\\${item_list.unit_price}  \${item_list.number_of_items}点  \\\${item_list.price}\n",
                                        TextParameter()
                                            .setWidth(
                                                32,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                            )
                            .actionPrintText(
                                "－－－－－－－－－－－－－－－－\n"
                            )
                            .actionPrintText(
                                "小計"
                            )
                            .actionPrintText(
                                "\${total_number_of_items}点  \\\${subtotal_price}\n",
                                TextParameter()
                                    .setWidth(
                                        28,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                )
                            )
                            .actionFeed(2.0)
                            .actionPrintText(
                                "合計"
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "\\\${total_price}\n",
                                TextParameter()
                                    .setWidth(
                                        28,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                )
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "(内消費税等"
                            )
                            .actionPrintText(
                                "\\\${total_tax})\n",
                                TextParameter()
                                    .setWidth(
                                        21,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                )
                            )
                            .actionPrintText(
                                "(10%標準対象"
                            )
                            .actionPrintText(
                                "\\\${tax_rate_10_target})\n",
                                TextParameter()
                                    .setWidth(
                                        20,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                )
                            )
                            .actionPrintText(
                                "( 内消費税等"
                            )
                            .actionPrintText(
                                "\\\${tax_rate_10_tax})\n",
                                TextParameter()
                                    .setWidth(
                                        20,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                )
                            )
                            .actionPrintText(
                                "("
                            )
                            .actionPrintText(
                                "\${payment_method}",
                                TextParameter()
                                    .setWidth(
                                        10,
                                        TextWidthParameter()
                                            .setWidthType(TextWidthType.Full)
                                    )
                            )
                            .actionPrintText(
                                "\\\${payment_amount})\n",
                                TextParameter()
                                    .setWidth(
                                        11,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                )
                            )
                            .actionPrintText(
                                "お預かり"
                            )
                            .actionPrintText(
                                "\\\${deposit_amount}\n",
                                TextParameter()
                                    .setWidth(
                                        24,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                )
                            )
                            .actionPrintText(
                                "お釣り"
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "\\\${change_amount}\n",
                                TextParameter()
                                    .setWidth(
                                        26,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                )
                            )
                            .styleAlignment(Alignment.Center)
                            .styleBold(false)
                            .actionFeed(2.0)
                            .actionPrintText(
                                "上記正に領収いたしました\n"
                            )
                            .actionFeedLine(1)
                            .styleAlignment(Alignment.Left)
                            .actionPrintText(
                                "\${note}\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}