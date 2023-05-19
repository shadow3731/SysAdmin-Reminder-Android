package app.phovdewae.sysadminreminder.tasks

import android.content.Context
import app.phovdewae.sysadminreminder.util.counter
import app.phovdewae.sysadminreminder.util.stringToDateTime
import phovdewae.sysadminreminder.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.collections.ArrayList

class TaskCloud {

    private val fileName = "tasks.txt"

    fun saveTasksToFile(tasks: ArrayList<Task>, context: Context): Boolean {
        return try {
            val directory = File(context.getExternalFilesDir(null), "task")
            directory.mkdirs()

            val file = File(directory, fileName)
            val fileOutput = FileOutputStream(file)
            fileOutput.write(arrayListToString(tasks).toByteArray())
            fileOutput.close()

            true
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

    fun loadTasksFromFile(context: Context): ArrayList<Task>? {
        return try {
            val directory = File(context.getExternalFilesDir(null), "task")
            if (!directory.exists()) directory.mkdirs()

            val file = File(directory, fileName)

            stringToArrayList(file.readText(), context)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun arrayListToString(tasks: ArrayList<Task>): String {
        var list = ""

        for (i in 0 until tasks.size) {
            list += "${tasks[i].toStringForFile()}\n"
        }

        return list
    }

    private fun stringToArrayList(rawString: String, context: Context): ArrayList<Task> {
        val patterns = arrayOf(
            Regex("[^\\n]+"),
            Regex("[^|]+")
        )

        val matchResultOuter = patterns[0].findAll(rawString)
        val tasks = ArrayList<Task>()
        for (matchOuter in matchResultOuter) {
            val taskObject = matchOuter.value
            val matchResultInner = patterns[1].findAll(taskObject)
            var i = 0
            val task = Task()
            for (matchInner in matchResultInner) {
                when (i) {
                    0 -> task.id = matchInner.value.toLong()
                    1 -> task.description = matchInner.value
                    2 -> task.executionTime = stringToDateTime(matchInner.value)
                    3 -> task.priority = matchInner.value
                    4 -> task.status = matchInner.value
                }
                i++
            }
            counter++
            if (task.status == context.getString(R.string.active_task)) tasks.add(task)
        }

        return tasks
    }
}