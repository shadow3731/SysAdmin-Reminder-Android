package phovdewae.sysadminreminder.util

import android.view.View
import com.google.android.material.bottomnavigation.BottomNavigationView
import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.R

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