package phovdewae.sysadminreminder.tasks

import java.util.Date

data class Task(val id: Long, val description: String, val executionTime: Date, val priority: String)