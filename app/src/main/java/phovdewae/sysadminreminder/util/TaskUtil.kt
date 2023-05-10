package phovdewae.sysadminreminder.util

import phovdewae.sysadminreminder.tasks.Task
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random

var counter = 0
val priorities = arrayOf("Little", "Low", "Medium", "High", "Critical")
fun generateTask(): Task {
    return Task((++counter).toLong(),
        "Description $counter",
        Date(),
        priorities[Random().nextInt(priorities.size)])
}

fun getFormattedDate(date: Date): String {
    val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return dateFormat.format(date)
}