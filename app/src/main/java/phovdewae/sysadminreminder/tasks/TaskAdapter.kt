package phovdewae.sysadminreminder.tasks

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.notifications.NotificationConfigurator
import phovdewae.sysadminreminder.util.counter
import phovdewae.sysadminreminder.util.dateTimeToString
import phovdewae.sysadminreminder.util.lastState
import phovdewae.sysadminreminder.util.makeBackground
import phovdewae.sysadminreminder.util.taskTimerPerformer
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.TaskItemBinding
import phovdewae.sysadminreminder.util.defineStatusId
import phovdewae.sysadminreminder.util.priorityList
import phovdewae.sysadminreminder.util.statusList

class TaskAdapter(
    private val mainActivity: MainActivity,
    private val taskCloud: TaskCloud,
    private val notificationConfigurator: NotificationConfigurator
    ): RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private var taskList = ArrayList<Task>()
    private var activeTaskList = ArrayList<Task>()

    inner class TaskHolder(item: View): RecyclerView.ViewHolder(item) {

        private val holderBinding = TaskItemBinding.bind(item)

        fun bind(task: Task, isExecutable: Boolean) = with(holderBinding) {
            tvId.text = task.id.toString()

            if (isExecutable) tvDescription.text = task.description
            else {
                val text = "(${statusList[task.statusId!!]}) ${task.description}"
                tvDescription.text = text
            }

            if (task.executionTime != null)
                tvExecutionTime.text = dateTimeToString(task.executionTime!!)

            tvPriority.text = priorityList[task.priorityId!!]

            if (isExecutable) {
                makeBackground(taskTimerPerformer.getColor(task.executionTime!!), cvTaskField)
                taskTimerPerformer.addTimers(
                    null,
                    task,
                    cvTaskField,
                    mainActivity,
                    notificationConfigurator
                )
            } else {
                makeBackground(Color.WHITE, cvTaskField)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.task_item, parent, false)
        view.isEnabled = parent.isEnabled
        return TaskHolder(view)
    }

    override fun getItemCount(): Int {
        return when(lastState) {
            R.id.tasks -> activeTaskList.size
            R.id.tasks_history -> taskList.size
            else -> activeTaskList.size
        }
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        when (lastState) {
            R.id.tasks -> {
                if (activeTaskList[position].statusId == defineStatusId(mainActivity.getString(R.string.status_active))) {
                    holder.bind(activeTaskList[position], true)

                    holder.itemView.setOnLongClickListener {
                        val popupMenu = PopupMenu(mainActivity, it, Gravity.END)
                        popupMenu.inflate(R.menu.popup_menu)
                        popupMenu.setOnMenuItemClickListener { menuItem ->
                            when (menuItem.itemId) {
                                R.id.edit_task_popup -> mainActivity.cardViewActivity
                                    .onChange(
                                        activeTaskList[position],
                                        mainActivity.binding,
                                        mainActivity,
                                        mainActivity.bottomNavigationViewActivity,
                                        mainActivity.recyclerViewActivity,
                                        this,
                                        taskCloud
                                    )

                                R.id.finish_task_popup -> deleteTask(position, true)
                                R.id.cancel_task_popup -> deleteTask(position, false)
                            }
                            true
                        }
                        popupMenu.show()
                        true
                    }
                }
            }
            R.id.tasks_history -> holder.bind(taskList[position], false)
        }
    }

    fun addTask(task: Task) {
        taskList.add(task)
        activeTaskList.add(task)

        if (taskCloud.saveTasksToFile(taskList, mainActivity)) {
            notifyItemInserted(activeTaskList.size - 1)
        }
    }

    fun addTasks(tasks: ArrayList<Task>) {
        taskList = tasks
        for (task in taskList) {
            if (task.statusId == defineStatusId(mainActivity.getString(R.string.status_active))) {
                activeTaskList.add(task)
            }
            counter++
        }
        notifyItemRangeInserted(0, activeTaskList.size)
    }

    fun editTask(task: Task) {
        val position = activeTaskList.indexOf(activeTaskList.find { it.id == task.id })
        if (task.executionTime != null)
            taskTimerPerformer.editTimers(position, task, mainActivity, notificationConfigurator)
        if (taskCloud.saveTasksToFile(taskList, mainActivity)) {
            notifyItemChanged(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteTask(position: Int, isFinished: Boolean) {
        if (isFinished) activeTaskList[position].statusId = defineStatusId(
            mainActivity.getString(R.string.status_finished)
        )
        else activeTaskList[position].statusId = defineStatusId(
            mainActivity.getString(R.string.status_cancelled)
        )

        if (taskCloud.saveTasksToFile(taskList, mainActivity)) {
            activeTaskList.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
            taskTimerPerformer.deleteTimers(position, false)
        }
    }

    fun deleteAllTasks() {
        taskList.clear()
        activeTaskList.clear()
        taskTimerPerformer.deleteAllTimers()
        counter = 0
    }
}