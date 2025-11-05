package com.starmicronics.starxpandsdk

import android.view.View
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding

class Util {
    companion object {
        fun setPadding(view: View) {
            ViewCompat.setOnApplyWindowInsetsListener(view) { v, inset ->
                val bars = inset.getInsets(
                    WindowInsetsCompat.Type.systemBars()
                            or
                            WindowInsetsCompat.Type.displayCutout()
                )
                v.updatePadding(
                    left = bars.left,
                    top = bars.top,
                    right = bars.right,
                    bottom = bars.bottom,
                )
                WindowInsetsCompat.CONSUMED
            }
        }
    }
}