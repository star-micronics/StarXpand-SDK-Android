package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CjkCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.ImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.InternationalCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthType
import com.starmicronics.starxpandsdk.R

class ReceiptSample08_Lunch2_Template {
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
                            .styleAlignment(Alignment.Right)
                            .actionPrintText(
                                "\${number}\n" +
                                        "会計日:\${account_day}\n"
                            )
                            .actionFeed(3.0)
                            .styleAlignment(Alignment.Left)
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Center)
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(2,2))
                                    .actionPrintText(
                                        "領 収 書\n"
                                    )
                            )
                            .actionFeed(3.0)
                            .styleUnderLine(true)
                            .actionPrintText(
                                "\${customer_name}",
                                TextParameter()
                                    .setWidth(
                                        15,
                                        TextWidthParameter()
                                            .setWidthType(TextWidthType.Full)
                                    )
                            )
                            .actionPrintText(
                                "様\n"
                            )
                            .styleUnderLine(false)
                            .actionFeedLine(2)
                            .actionPrintText(
                                "領収金額\n"
                            )
                            .actionPrintText(
                                "        "
                            )
                            .styleBold(true)
                            .styleUnderLine(true)
                            .actionPrintText(
                                "\\\${total_price}-\n",
                                TextParameter()
                                    .setWidth(
                                        24,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .styleBold(false)
                            .styleUnderLine(false)
                            .actionPrintText(
                                "     (10%標準対象"
                            )
                            .actionPrintText(
                                "\\\${tax_rate_10_target})\n",
                                TextParameter()
                                    .setWidth(
                                        15,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintText(
                                "     ( 内消費税等"
                            )
                            .actionPrintText(
                                "\\\${tax_rate_10_tax})\n",
                                TextParameter()
                                    .setWidth(
                                        15,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .styleAlignment(Alignment.Center)
                            .actionFeedLine(1)
                            .actionPrintText(
                                "上記正に領収いたしました\n"
                            )
                            .actionFeedLine(2)
                            .styleAlignment(Alignment.Left)
                            .styleUnderLine(true)
                            .actionPrintText(
                                "但 "
                            )
                            .actionPrintText(
                                "\${description}",
                                TextParameter()
                                    .setWidth(
                                        11,
                                        TextWidthParameter()
                                            .setWidthType(TextWidthType.Full)
                                    )
                            )
                            .actionPrintText(
                                " として"
                            )
                            .actionFeedLine(3)
                            .styleUnderLine(false)
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(2,2))
                                    .actionPrintText(
                                        "\${store_name}\n"
                                    )
                            )
                            .actionPrintText(
                                "\${address}\n" +
                                        "TEL:\${telephone_number}\n" +
                                        "登録番号:\${registration_number}\n" +
                                        "\n" +
                                        "担当者:\${staff_name}\n" +
                                        "領収書No:\n" +
                                        "\${receipt_number}\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}