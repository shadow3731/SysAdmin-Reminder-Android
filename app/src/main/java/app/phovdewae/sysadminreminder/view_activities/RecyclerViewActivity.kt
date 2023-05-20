package app.phovdewae.sysadminreminder.view_activities

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.phovdewae.sysadminreminder.tasks.TaskAdapter
import app.phovdewae.sysadminreminder.tasks.TaskCloud

class RecyclerViewActivity(private val recyclerView: RecyclerView) {

    fun showTasks(
        context: Context,
        taskAdapter: TaskAdapter,
        taskCloud: TaskCloud,
        showOnlyActive: Boolean
    ) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = taskAdapter

        taskAdapter.deleteAllTasks()

        val tasks = if (showOnlyActive) taskCloud.loadTasksFromFile(context, true)
        else taskCloud.loadTasksFromFile(context, false)

        if (tasks != null && tasks.size > 0) taskAdapter.addTasks(tasks)
    }
}