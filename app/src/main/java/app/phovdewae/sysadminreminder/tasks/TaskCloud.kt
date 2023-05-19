package app.phovdewae.sysadminreminder.tasks

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.lang.Exception
import java.util.ArrayList
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
            directory.mkdirs()

            val file = File(directory, fileName)

            stringToArrayList(file.readText())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun arrayListToString(tasks: ArrayList<Task>): String {
        var list = ""

        for (i in 0 until tasks.size) {
            list += "${tasks[i]};\n"
        }

        return list
    }

    private fun stringToArrayList(rawString: String): ArrayList<Task> {
        val patterns = arrayOf(
            Regex("[^);\\n]+"),
            Regex("[Task(]"
            )



        val matchResult = pattern1.findAll(rawString)
        val objects = ArrayList<String>()
        for (match in matchResult) {
            objects.add(match.value)
        }


    }
}