package phovdewae.sysadminreminder.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View

fun makeLinearBorder(vararg elements: View) {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.setStroke(2, Color.BLACK)
    shape.setColor(Color.TRANSPARENT)
    shape.cornerRadius = 15F

    for (element in elements) element.background = shape
}