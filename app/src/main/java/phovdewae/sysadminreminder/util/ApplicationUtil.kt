package phovdewae.sysadminreminder.util

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.settings.Settings
import phovdewae.sysadminreminder.tasks.TaskAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import phovdewae.sysadminreminder.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

var lastState = R.id.tasks
var settings = Settings()

fun View.enable(isChangeableBackground: Boolean, context: Context) {
    enableElement(this)
    if (isChangeableBackground)
        background = ContextCompat.getDrawable(context, R.drawable.background)
}

fun View.disable(isChangeableBackground: Boolean) {
    disableElement(this)
    if (isChangeableBackground) makeBackground(R.color.dark_grey, this)
}

fun View.hideKeyboard(context: Context) {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
}

fun RecyclerView.enable(
    isChangeableBackground: Boolean,
    context: Context,
    taskAdapter: TaskAdapter
) {
    enableElement(this)
    if (isChangeableBackground)
        background = ContextCompat.getDrawable(context, R.drawable.background)
    adapter = taskAdapter
}

fun RecyclerView.disable(isChangeableBackground: Boolean, taskAdapter: TaskAdapter) {
    disableElement(this)
    if (isChangeableBackground) makeBackground(R.color.dark_grey, this)
    adapter = taskAdapter
}

fun BottomNavigationView.enable() {
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

fun EditText.enable(isChangeableBackground: Boolean, context: Context) {
    enableElement(this)
    if (isChangeableBackground)
        background = ContextCompat.getDrawable(context, R.drawable.background)
}

fun EditText.disable(isChangeableBackground: Boolean) {
    disableElement(this)
    if (isChangeableBackground) makeBackground(R.color.dark_grey, this)
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
            val selectedDateString = SimpleDateFormat(datePattern, Locale.getDefault())
                .format(selectedDate.time)
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
            val selectedTimeString = SimpleDateFormat("HH:mm", Locale.getDefault())
                .format(selectedTime.time)
            editText.setText(selectedTimeString)
        },
        Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        Calendar.getInstance().get(Calendar.MINUTE),
        true
    )
}

fun Button.enable(isChangeableBackground: Boolean, context: Context) {
    enableElement(this)
    if (isChangeableBackground)
        background = ContextCompat.getDrawable(context, R.drawable.background)
}

fun Button.disable(isChangeableBackground: Boolean) {
    disableElement(this)
    if (isChangeableBackground) makeBackground(R.color.dark_grey, this)
    setOnClickListener(null)
}

fun SwitchCompat.enable(isChangeableBackground: Boolean, context: Context) {
    enableElement(this)
    if (isChangeableBackground)
        background = ContextCompat.getDrawable(context, R.drawable.background)
}

fun SwitchCompat.disable(isChangeableBackground: Boolean) {
    disableElement(this)
    if (isChangeableBackground) makeBackground(R.color.dark_grey, this)
    setOnCheckedChangeListener(null)
}

private fun enableElement(element: View) {
    element.isEnabled = true
    element.isFocusable = true
    element.isClickable = true
    element.isFocusableInTouchMode = true
    element.alpha = 1F
}

private fun disableElement(element: View) {
    element.isEnabled = false
    element.isFocusable = false
    element.isClickable = false
    element.isFocusableInTouchMode = false
    element.alpha = 0.75F
}