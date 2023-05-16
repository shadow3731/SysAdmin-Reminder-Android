package phovdewae.sysadminreminder.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.widget.EditText
import com.google.android.material.bottomnavigation.BottomNavigationView
import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

var lastState = R.string.tasks_name

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

fun BottomNavigationView.move(toDefault: Boolean, activity: MainActivity) {
    if (toDefault) {
        menu.findItem(R.id.tasks).isChecked = true
        activity.title = activity.getString(R.string.tasks_name)
        lastState = R.id.tasks
    } else {
        menu.findItem(lastState).isChecked = true
        activity.title = when (selectedItemId) {
            R.id.tasks -> activity.getString(R.string.tasks_name)
            R.id.tasks_history -> activity.getString(R.string.tasks_history_name)
            else -> activity.getString(R.string.tasks_name)
        }
    }
}

fun EditText.prepareForDateTime(context: Context, isDate: Boolean) {
    isFocusable = false
    isClickable = true
    setOnFocusChangeListener { _, hasFocus ->
        if (hasFocus) {
            if (isDate) getDatePickerDialog(this, context).show()
            else getTimePickerDialog(this, context).show()
        }
    }
    setOnClickListener {
        if (isDate) getDatePickerDialog(this, context).show()
        else getTimePickerDialog(this, context).show()
    }
}

private fun getDatePickerDialog(editText: EditText, context: Context): DatePickerDialog {
    return DatePickerDialog(
        context,
        { _, year, month, day ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, day)
            val selectedDateString = SimpleDateFormat(datePattern, Locale.getDefault()).format(selectedDate.time)
            editText.setText(selectedDateString)
        },
        Calendar.getInstance().get(Calendar.YEAR),
        Calendar.getInstance().get(Calendar.MONTH),
        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
    )
}

private fun getTimePickerDialog(editText: EditText, context: Context): TimePickerDialog {
    return TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val selectedTime = Calendar.getInstance()
            selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            selectedTime.set(Calendar.MINUTE, minute)
            val selectedTimeString = SimpleDateFormat("HH:mm", Locale.getDefault()).format(selectedTime.time)
            editText.setText(selectedTimeString)
        },
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        Calendar.getInstance().get(Calendar.MINUTE),
        true
    )
}