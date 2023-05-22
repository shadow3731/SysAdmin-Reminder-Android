package app.phovdewae.sysadminreminder.util

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

fun dateToString(srcDate: Date): String {
    val dateTimeFormat = SimpleDateFormat(datePattern, Locale.getDefault())
    return dateTimeFormat.format(srcDate)
}

fun timeToString(srcTime: Date): String {
    val dateTimeFormat = SimpleDateFormat(timePattern, Locale.getDefault())
    return dateTimeFormat.format(srcTime)
}

fun stringToDateTime(srcDate: String, srcTime: String): Date {
    val dateTimeFormat = SimpleDateFormat("$datePattern $timePattern", Locale.getDefault())
    dateTimeFormat.isLenient = false
    return dateTimeFormat.parse("$srcDate $srcTime")!!
}

fun stringToDateTime(srcDateTime: String): Date {
    val dateTimeFormat = SimpleDateFormat("$datePattern $timePattern", Locale.getDefault())
    dateTimeFormat.isLenient = false
    return dateTimeFormat.parse(srcDateTime)!!
}

fun getPriorities(context: Context): Array<String> {
    return context.resources.getStringArray(R.array.priorities)
}

fun definePriorityId(context: Context, priority: String): Int {
    val index = getPriorities(context).indexOf(priority)
    return if (index > -1) index else 0
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

fun String.isEmptyField(isInputOperation: Boolean): String {
    return if (isInputOperation) this.ifEmpty { "" }
    else if (this == "null") "" else this
}

fun isNullablePassword(password: ByteArray?): String {
    return password?.toString() ?: "null"
}