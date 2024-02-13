package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.ImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter
import com.starmicronics.starxpandsdk.R

class ReceiptSample06_PharmacyStore2_Template {
    companion object {
        fun createReceiptTemplate(context: Context): String {
            val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .settingPrintableArea(72.0)
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .styleLineSpace(3.0)
                            .actionPrintImage(
                                ImageParameter(logoBitmap, 300)
                            )
                            .actionPrintText(
                                "\${address}\n" + "\${telephone_number}"
                            )
                            .styleAlignment(Alignment.Left)
                            .actionFeedLine(1)
                            .actionPrintText(
                                "208            7820   0021"
                            )
                            .styleHorizontalPositionTo(0.0)
                            .actionPrintText(
                                "\${datetime}\n",
                                TextParameter()
                                    .setWidth(
                                        48,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionFeedLine(1)
                            .styleAlignment(Alignment.Center)
                            .styleBold(true)
                            .actionPrintText(
                                "<< BUY 1 GET 1 EQUAL/LESS VALUE 50% OFF >>\n"
                            )
                            .styleAlignment(Alignment.Left)
                            .styleBold(false)
                            .add(
                                PrinterBuilder(
                                    PrinterParameter()
                                        .setTemplateExtension(
                                            TemplateExtensionParameter()
                                                .setEnableArrayFieldData(true)
                                        )
                                )
                                    .actionPrintText(
                                        "\${item_list1.name}\n",
                                        TextParameter()
                                            .setWidth(48)
                                    )
                                    .actionPrintText(
                                        "    \${item_list1.product_number}",
                                        TextParameter()
                                            .setWidth(30)
                                    )
                                    .actionPrintText(
                                        " "
                                    )
                                    .actionPrintText(
                                        "\${item_list1.tax_mark}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .actionPrintText(
                                        "\${item_list1.price%.2f}",
                                        TextParameter()
                                            .setWidth(
                                                10,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                                    .actionPrintText(
                                        " \${item_list1.remarks}\n",
                                        TextParameter()
                                            .setWidth(6)
                                    )
                                    .actionPrintText(
                                        "\${item_list1.detail1}" +
                                                "\${item_list1.detail2}" +
                                                "\${item_list1.detail3}"
                                    )
                            )
                            .actionPrintText(
                                "************************************************\n"
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
                                        "\${item_list2.name}\n",
                                        TextParameter()
                                            .setWidth(48)
                                    )
                                    .actionPrintText(
                                        "    \${item_list2.product_number}",
                                        TextParameter()
                                            .setWidth(30)
                                    )
                                    .actionPrintText(
                                        " "
                                    )
                                    .actionPrintText(
                                        "\${item_list2.tax_mark}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .actionPrintText(
                                        "\${item_list2.price%.2f}",
                                        TextParameter()
                                            .setWidth(
                                                10,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                                    .actionPrintText(
                                        " \${item_list2.remarks}\n",
                                        TextParameter()
                                            .setWidth(6)
                                    )
                                    .actionPrintText(
                                        "\${item_list2.detail1}" +
                                                "\${item_list2.detail2}" +
                                                "\${item_list2.detail3}" +
                                                "\${item_list2.detail4}"
                                    )
                            )
                            .actionFeedLine(1)
                            .actionPrintText(
                                "    SUBTOTAL"
                            )
                            .actionPrintText(
                                "\${subtotal%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        30,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintText(
                                "    SALES TAX A=6.75%"
                            )
                            .actionPrintText(
                                "\${sales_tax%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        21,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionFeedLine(1)
                            .actionPrintText(
                                "    TOTAL"
                            )
                            .actionPrintText(
                                "\${total_price%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        33,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintText(
                                "    \${payment_method}",
                                TextParameter()
                                    .setWidth(24)
                            )
                            .actionPrintText(
                                "\${payment_amount%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        18,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintText(
                                "    CHANGE"
                            )
                            .actionPrintText(
                                "\${change%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        32,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionFeedLine(3)
                            .actionPrintText(
                                "MYSTAR SAVINGS"
                            )
                            .actionPrintText(
                                "\${my_serving%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        28,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionPrintText(
                                "MFG COUPON SAVINGS"
                            )
                            .actionPrintText(
                                "\${mfg_serving%.2f}\n",
                                TextParameter()
                                    .setWidth(
                                        24,
                                        TextWidthParameter()
                                            .setAlignment(TextAlignment.Right)
                                    )
                            )
                            .actionFeedLine(1)
                            .actionPrintText(
                                "THANK YOU FOR SHOPPING AT STAR SHOP\n"
                            )
                            .actionFeedLine(1)
                            .actionPrintText(
                                "\${note}\n"
                            )
                            .actionFeedLine(1)
                            .styleAlignment(Alignment.Center)
                            .actionPrintText(
                                "RFN# \${rfn}\n"
                            )
                            .actionPrintBarcode(
                                BarcodeParameter("\${barcode}", BarcodeSymbology.Code128)
                                    .setBarDots(1)
                            )
                            .actionFeedLine(1)
                            .actionPrintText(
                                "************************************************\n"
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}