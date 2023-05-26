package phovdewae.sysadminreminder.view_activities

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import phovdewae.sysadminreminder.tasks.TaskAdapter
import phovdewae.sysadminreminder.tasks.TaskCloud
import phovdewae.sysadminreminder.util.counter

class RecyclerViewActivity(private val recyclerView: RecyclerView) {

    fun showTasks(context: Context, taskAdapter: TaskAdapter, taskCloud: TaskCloud) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = taskAdapter

        taskAdapter.deleteAllTasks()

        val tasks = taskCloud.loadTasksFromFile(context)
        if (tasks != null && tasks.size > 0) taskAdapter.addTasks(tasks)
    }
}