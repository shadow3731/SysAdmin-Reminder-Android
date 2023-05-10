package phovdewae.sysadminreminder.util

import phovdewae.sysadminreminder.tasks.Task
import java.util.Date
import java.util.Random

class TaskUtil {

    private var counter = 0
    private val priorities = arrayOf("Little", "Low", "Medium", "High", "Critical")

    fun generateTask(): Task {
        return Task((++counter).toLong(),
            "Description $counter",
            Date(),
            priorities[Random().nextInt(priorities.size)])
    }
}