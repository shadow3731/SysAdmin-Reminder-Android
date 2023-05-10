package phovdewae.sysadminreminder.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

fun getLinearBorder(): GradientDrawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.setStroke(2, Color.BLACK)
    shape.setColor(Color.TRANSPARENT)
    shape.cornerRadius = 15F

    return shape
}