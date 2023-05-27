package phovdewae.sysadminreminder.tasks

import phovdewae.sysadminreminder.util.dateTimeToString
import java.util.Date

class Task(
    var id: Long?,
    var description: String?,
    var executionTime: Date?,
    var priorityId: Int?,
    var statusId: Int?
    ) {

    constructor() : this(null, null, null, null, null)

    fun toStringForFile(): String {
        return "||$id||$description||${
            dateTimeToString(executionTime!!)
        }||$priorityId||$statusId||"
    }

    override fun toString(): String {
        return "Task(" +
                "id=$id, " +
                "description=$description, " +
                "executionTime=$executionTime, " +
                "priority=$priorityId, " +
                "status=$statusId)"
    }
}