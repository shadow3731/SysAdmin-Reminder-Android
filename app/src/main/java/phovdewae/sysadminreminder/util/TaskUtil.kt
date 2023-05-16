package phovdewae.sysadminreminder.util

import android.content.Context
import phovdewae.sysadminreminder.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

var counter = 0
var datePattern = "dd.MM.yyyy"
var timePattern = "HH:mm"
//fun generateTask(): Task {
//    return Task((++counter).toLong(),
//        "Description $counter",
//        Date(),
//        priorities[Random().nextInt(priorities.size)])
//}

fun getFormattedDateTime(date: Date): String {
    val dateFormat = SimpleDateFormat("$datePattern $timePattern", Locale.getDefault())
    return dateFormat.format(date)
}

fun getPriorities(context: Context): Array<String> {
    return arrayOf(context.getString(R.string.priority_little),
        context.getString(R.string.priority_low),
        context.getString(R.string.priority_medium),
        context.getString(R.string.priority_high),
        context.getString(R.string.priority_critical))
}