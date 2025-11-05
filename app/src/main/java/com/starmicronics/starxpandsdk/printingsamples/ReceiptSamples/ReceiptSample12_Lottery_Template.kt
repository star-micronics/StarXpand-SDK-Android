package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.MagnificationParameter
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.BarcodeSymbology
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.ImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.starxpandsdk.R

class ReceiptSample12_Lottery_Template {
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
                            .actionPrintImage(ImageParameter(logoBitmap,400))
                            .actionFeedLine(1)
                            .add(
                                PrinterBuilder(
                                    PrinterParameter()
                                        .setTemplateExtension(
                                            TemplateExtensionParameter()
                                                .setEnableArrayFieldData(true)
                                        )
                                )
                                    .styleBold(true)
                                    .actionPrintText(
                                        "\${item_list.header}",
                                        TextParameter()
                                            .setWidth(1)
                                    )
                                    .styleMagnification(MagnificationParameter(2,2))
                                    .actionPrintText(
                                        " \${item_list.number_1%02d}" +
                                                " \${item_list.number_2%02d}" +
                                                " \${item_list.number_3%02d}" +
                                                " \${item_list.number_4%02d}" +
                                                " \${item_list.number_5%02d}"
                                    )
                                    .styleMagnification(MagnificationParameter(1,1))
                                    .actionPrintText(
                                        " OP"
                                    )
                                    .styleMagnification(MagnificationParameter(2,2))
                                    .actionPrintText(
                                        " \${item_list.number_6%02d}"
                                    )
                                    .styleMagnification(MagnificationParameter(1,1))
                                    .actionPrintText(
                                        " OP\n"
                                    )
                            )
                            .actionFeedLine(1)
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .styleMagnification(MagnificationParameter(2,2))
                                    .actionPrintText(
                                        "\${winning_day}\n" +
                                                "$\${price%.2f}\n"
                                    )
                            )
                            .actionFeedLine(1)
                            .actionPrintText(
                                "\${sales_number}\n" +
                                        "\${datetime}\n"
                            )
                            .actionFeedLine(1)
                            .actionPrintText(
                                "\${note}\n"
                            )
                            .actionPrintBarcode(
                                BarcodeParameter("\${barcode}", BarcodeSymbology.Code128)
                                    .setHeight(7.0)
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}