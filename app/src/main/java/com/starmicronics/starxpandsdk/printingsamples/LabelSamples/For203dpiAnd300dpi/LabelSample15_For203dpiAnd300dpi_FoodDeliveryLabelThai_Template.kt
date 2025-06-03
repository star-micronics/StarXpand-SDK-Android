package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.TemplateExtensionParameter
import com.starmicronics.stario10.starxpandcommand.printer.InternationalCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnification
import com.starmicronics.stario10.starxpandcommand.printer.BaseMagnificationParameter
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeModel
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeLevel
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.PrinterParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextAlignment
import com.starmicronics.stario10.starxpandcommand.printer.TextParameter
import com.starmicronics.stario10.starxpandcommand.printer.TextWidthParameter
import com.starmicronics.starxpandsdk.R

class LabelSample15_For203dpiAnd300dpi_FoodDeliveryLabelThai_Template {
    companion object {
        fun createFoodDeliveryThaiReceipt(context: Context): String {
            val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    // Change the printable area setting for this layout according to the printer resolution.
                    // 48.0 for 203dpi, 48.7 for 300dpi
                    .settingPrintableArea(48.0)
                    //.settingPrintableArea(48.7)
                    .addPrinter(
                        PrinterBuilder()
                            // By setting the base print size of text to x1.5 for 300dpi,
                            // you can print text at the same size as 203dpi.
                            //.styleBaseMagnification(BaseMagnificationParameter().setText(BaseMagnification.X1_5))
                            .styleAlignment(Alignment.Center)
                            .styleBold(true)
                            .styleCharacterSpace(0.0)
                            .styleInternationalCharacter(InternationalCharacterType.Usa)
                            .actionPrintText(
                                "สลิปการสั่งซื้อ\n"
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "\${store_name}  \n" +
                                        "\${address} \n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .actionPrintText(
                                        "#\${number%04u}\n"
                                    )
                            )
                            .actionPrintText(
                                "วันที่:\${date}  เวลา:\${time}\n" +
                                        "--------------------------------\n"
                            )
                            .styleAlignment(Alignment.Left)
                            .actionPrintText(
                                "ลูกค้า: "
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "\${customer_name} \n"
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "ชำระเงินแล้ว \n"+
                                        "เวลาที่รับออเดอร์:\${order_time}\n" +
                                        "--------------------------------\n" +
                                        "\n"
                            )
                            .add(
                                PrinterBuilder(
                                    PrinterParameter()
                                        .setTemplateExtension(
                                            TemplateExtensionParameter()
                                                .setEnableArrayFieldData(true)
                                        )
                                )
                                    .styleHorizontalTabPositions(listOf(3, 18, 23))
                                    .actionPrintText(
                                        "\${item_list.number_of_items}X\t\${item_list.name}\t"
                                    )
                                    .actionPrintText(
                                        "\${item_list.price}",
                                        TextParameter()
                                            .setWidth(
                                                4,
                                                TextWidthParameter()
                                                    .setAlignment(TextAlignment.Right)
                                            )
                                    )
                                    .actionPrintText(
                                        "\tบาท\n",
                                    )
                            )
                            .actionPrintText(
                                "--------------------------------\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Center)
                                    .actionPrintText(
                                        "\${note}\n"
                                    )
                            )
                            .actionPrintText(
                                "--------------------------------\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleHorizontalTabPositions(listOf(26))
                                    .actionPrintText(
                                        "รวม\t"
                                    )
                                    .styleBold(true)
                                    .actionPrintText(
                                        "\${sub_total}",
                                    )
                                    .styleBold(false)
                                    .actionPrintText(
                                        "บาท\n"
                                    )
                                    .actionPrintText(
                                        "ภาษีมูลค่าเพิ่ม\t"
                                    )
                                    .actionPrintText(
                                        "\${tax}\n",
                                    )
                            )
                            .actionPrintText(
                                "--------------------------------\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Left)
                                    .styleBold(true)
                                    .actionPrintText(
                                        "รวมทั้งหมด\n"
                                    )
                                    .styleAlignment(Alignment.Right)
                                    .actionPrintText(
                                        "\${total} บาท\n")
                            )
                            .actionPrintText(
                                "--------------------------------\n" +
                                        "\n" +
                                        "Charge \${charge}\n" +
                                        "\n")
                            .styleAlignment(Alignment.Center)
                            .add(PrinterBuilder()
                                .styleInvert(true)
                                .actionPrintText(
                                    "การขอใบกำกับภาษี\n"
                                )
                            )
                            .actionPrintText(
                                "นี่ไม่ใช่ใบเสร็จรับเงินอย่างเป็นทางการ\n"+
                                        "และไม่สามารถใช้ในการคืนภาษีได้\n" +
                                        "โปรดขอใบกำกับภาษีอย่างเป็นทางการจากผู้ขาย\n" +
                                        "\n"
                            )
                            .actionFeedLine(1)
                            .actionPrintQRCode(
                                QRCodeParameter("\${store_url}")
                                    .setCellSize(8)
                                    .setLevel(QRCodeLevel.L)
                                    .setModel(QRCodeModel.Model2)
                            )
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }
    }
}
