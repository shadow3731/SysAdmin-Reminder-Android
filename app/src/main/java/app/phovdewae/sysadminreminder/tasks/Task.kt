package app.phovdewae.sysadminreminder.tasks

import app.phovdewae.sysadminreminder.util.dateTimeToString
import java.util.Date

class Task(
    var id: Long?,
    var description: String?,
    var executionTime: Date?,
    var priority: String?,
    var status: String?
    ) {

    constructor() : this(null, null, null, null, null)

    fun toStringForFile(): String {
        return "||$id||$description||${
            dateTimeToString(executionTime!!)}||$priority||$status||"
    }

    override fun toString(): String {
        return "Task(" +
                "id=$id, " +
                "description=$description, " +
                "executionTime=$executionTime, " +
                "priority=$priority, " +
                "status=$status)"
    }
}