package com.starmicronics.starxpandsdk.printingsamples

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import android.text.Layout
import android.text.StaticLayout
import android.text.TextPaint
import com.starmicronics.stario10.starxpandcommand.DocumentBuilder
import com.starmicronics.stario10.starxpandcommand.PrinterBuilder
import com.starmicronics.stario10.starxpandcommand.StarXpandCommandBuilder
import com.starmicronics.stario10.starxpandcommand.printer.Alignment
import com.starmicronics.stario10.starxpandcommand.printer.CutType
import com.starmicronics.stario10.starxpandcommand.printer.ImageParameter
import com.starmicronics.stario10.starxpandcommand.printer.InternationalCharacterType
import com.starmicronics.starxpandsdk.R


class GraphicSample15_GraphicReceipt {
    companion object {
        fun createGraphicReceipt(context: Context): String {
            val logo = BitmapFactory.decodeResource(context.resources, R.drawable.logo_01)

            val builder = StarXpandCommandBuilder()
            builder.addDocument(
                DocumentBuilder()
                    .addPrinter(
                        PrinterBuilder()
                            .styleAlignment(Alignment.Center)
                            .actionPrintImage(ImageParameter(logo, 406))
                            .actionPrintImage(
                                createImageParameterFromText(
                                    "Star Clothing Boutique\n" +
                                         "123 Star Road\n" +
                                         "City, State 12345\n" +
                                         "\n" +
                                         "Date:MM/DD/YYYY Time:HH:MM PM\n" +
                                         "-----------------------------\n" +
                                         "\n" +
                                         "SKU       Description   Total\n" +
                                         "300678566 PLAIN T-SHIRT 10.99\n" +
                                         "300692003 BLACK DENIM   29.99\n" +
                                         "300651148 BLUE DENIM    29.99\n" +
                                         "300642980 STRIPED DRESS 49.99\n" +
                                         "300638471 BLACK BOOTS   35.99\n" +
                                         "\n" +
                                         "Subtotal               156.95\n" +
                                         "Tax                      0.00\n" +
                                         "-----------------------------\n" +
                                         "\n" +
                                         "Total                 $156.95\n" +
                                         "-----------------------------\n" +
                                         "\n" +
                                         "Charge\n" +
                                         "156.95\n" +
                                         "Visa XXXX-XXXX-XXXX-0123\n" +
                                         "\n"
                                ))
                            .actionCut(CutType.Partial)
                    )
            )
            return builder.getCommands()
        }

        private fun createImageParameterFromText(text: String):ImageParameter{
            val width = 384
            val typeface = Typeface.create(Typeface.MONOSPACE, Typeface.NORMAL)
            val bitmap = createBitmapFromText(text,22,width,typeface);
            return ImageParameter(bitmap,width)
        }

        private fun createBitmapFromText(
            text: String,textSize: Int,width: Int,typeface: Typeface?): Bitmap {
            val paint = Paint()
            val bitmap: Bitmap
            paint.textSize = textSize.toFloat()
            paint.typeface = typeface
            paint.getTextBounds(text, 0, text.length, Rect())
            val textPaint = TextPaint(paint)
            val builder = StaticLayout.Builder.obtain(text,0,text.length,textPaint,width)
                .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                .setLineSpacing(0f,1f)
                .setIncludePad(false)

            val staticLayout = builder.build()

            // Create bitmap
            bitmap = Bitmap.createBitmap(
                staticLayout.width,
                staticLayout.height,
                Bitmap.Config.ARGB_8888
            )

            // Create canvas
            val canvas: Canvas = Canvas(bitmap)
            canvas.drawColor(Color.WHITE)
            canvas.translate(0f, 0f)
            staticLayout.draw(canvas)
            return bitmap
        }
    }
}