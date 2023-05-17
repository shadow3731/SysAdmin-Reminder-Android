package phovdewae.sysadminreminder.util

import android.content.Context
import phovdewae.sysadminreminder.R
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

var counter = 0
var datePattern = "dd.MM.yyyy"
var timePattern = "HH:mm"

fun dateTimeToString(srcDateTime: Date): String {
    val dateTimeFormat = SimpleDateFormat("$datePattern $timePattern", Locale.getDefault())
    return dateTimeFormat.format(srcDateTime)
}

fun stringToDateTime(srcDate: String, srcTime: String): Date {
    val dateTimeFormat = SimpleDateFormat("$datePattern $timePattern", Locale.getDefault())
    dateTimeFormat.isLenient = false
    return dateTimeFormat.parse("$srcDate $srcTime")!!

}

fun getPriorities(context: Context): Array<String> {
    return arrayOf(context.getString(R.string.priority_little),
        context.getString(R.string.priority_low),
        context.getString(R.string.priority_medium),
        context.getString(R.string.priority_high),
        context.getString(R.string.priority_critical))
}

fun isValid(description: String, date: String, time: String): Boolean {
    if (description.isEmpty()) return false

    val dateTimeFormat = SimpleDateFormat("$datePattern $timePattern", Locale.getDefault())
    dateTimeFormat.isLenient = false
    try {
        dateTimeFormat.parse("$date $time")
    } catch (e: Exception) {
        return false
    }

    return true
}