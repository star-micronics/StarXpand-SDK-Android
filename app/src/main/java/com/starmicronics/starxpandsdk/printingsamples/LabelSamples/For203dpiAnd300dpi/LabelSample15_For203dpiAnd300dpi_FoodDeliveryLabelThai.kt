package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.BitmapFactory
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.InternationalCharacterType
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeParameter
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeModel
import com.starmicronics.stario10.starxpandcommand.printer.QRCodeLevel
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.starxpandsdk.R

class LabelSample15_For203dpiAnd300dpi_FoodDeliveryLabelThai {
    companion object {
        fun createFoodDeliveryThaiReceipt(context: Context): String {
            val logoBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .addPrinter(
                        PrinterBuilder()
                            .styleInternationalCharacter(InternationalCharacterType.Usa)
                            .styleCharacterSpace(0.0)
                            .styleAlignment(Alignment.Center)
                            .styleBold(true)
                            .actionPrintText(
                                "สลิปการสั่งซื้อ\n"
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "ร้าน STAR EAT  \n" +
                                        "Bangkok , Thailand \n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleBold(true)
                                    .actionPrintText(
                                        "#0001\n"
                                    )
                            )
                            .actionPrintText(
                                "วันที่:ดด/วว/ปปปป  เวลา:HH:MM PM\n" +
                                        "--------------------------------\n"
                            )
                            .styleAlignment(Alignment.Left)
                            .actionPrintText(
                                "ลูกค้า: "
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "Mr. Star Micronics \n"
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "ชำระเงินแล้ว \n"+
                                        "เวลาที่รับออเดอร์:HH:MM PM\n" +
                                        "--------------------------------\n" +
                                        "\n"
                            )
                            .actionPrintText(
                                "1X ข้าวผัดปู           59 บาท \n" +
                                        "1X ต้มยำกุ้ง          120 บาท  \n" +
                                        "2X ก๋วยเตี๋ยวหมูน้ำตก    69 บาท \n" +
                                        "--------------------------------\n"
                            )
                            .add(
                                PrinterBuilder()
                                    .styleAlignment(Alignment.Center)
                                    .actionPrintText(
                                        "**โปรดเตรียมช้อนส้อมให้ด้วย\n"
                                    )
                            )
                            .actionPrintText(
                                "--------------------------------\n" +
                                        "รวม                       "
                            )
                            .styleBold(true)
                            .actionPrintText(
                                "248"
                            )
                            .styleBold(false)
                            .actionPrintText(
                                "บาท\n" +
                                        "ภาษีมูลค่าเพิ่ม                - \n" +
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
                                    .actionPrintText("248 บาท\n")
                            )
                            .actionPrintText(
                                "--------------------------------\n" +
                                        "\n" +
                                        "Charge " +
                                        "Visa XXXX-XXXX-XXXX-0123\n" +
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
                                QRCodeParameter("https://starmicronics.co.th/en/contact/\n")
                                    .setModel(QRCodeModel.Model2)
                                    .setLevel(QRCodeLevel.L)
                                    .setCellSize(8)
                            )
                            .actionCut(CutType.Partial)
                    )
            )

            return builder.getCommands()
        }
    }
}
