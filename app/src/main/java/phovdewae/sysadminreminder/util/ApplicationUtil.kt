package phovdewae.sysadminreminder.util

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.R

fun getLinearBorder(): GradientDrawable {
    val shape = GradientDrawable()
    shape.shape = GradientDrawable.RECTANGLE
    shape.setStroke(2, Color.BLACK)
    shape.setColor(Color.TRANSPARENT)
    shape.cornerRadius = 15F

    return shape
}

fun View.enable() {
    isEnabled = true
    isClickable = true
    isFocusable = true
    alpha = 1F
}

fun View.disable() {
    isEnabled = false
    isClickable = false
    isFocusable = false
    alpha = 0.5F
}

fun BottomNavigationView.enable(mainActivity: MainActivity) {
    setOnItemSelectedListener {
        mainActivity.changeActivityNameOrAddTask(it.itemId)
        true
    }
    for (i in 0 until menu.size()) {
        menu.getItem(i).isEnabled = true
    }
}

fun BottomNavigationView.disable() {
    setOnItemSelectedListener(null)
    setOnItemReselectedListener(null)
    for (i in 0 until menu.size()) {
        menu.getItem(i).isEnabled = false
    }
}

fun BottomNavigationView.setDefault(activity: MainActivity) {
    menu.findItem(R.id.tasks).isChecked = true
    activity.title = activity.getString(R.string.tasks_name)
}