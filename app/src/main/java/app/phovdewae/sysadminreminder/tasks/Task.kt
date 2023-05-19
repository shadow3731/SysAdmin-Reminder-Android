package app.phovdewae.sysadminreminder.tasks

import java.io.Serializable
import java.util.Date

data class Task(
    val id: Long,
    var description: String,
    var executionTime: Date,
    var priority: String,
    var status: String
    ): Serializable