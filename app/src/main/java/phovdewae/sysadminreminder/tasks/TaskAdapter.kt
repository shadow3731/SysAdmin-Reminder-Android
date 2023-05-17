package phovdewae.sysadminreminder.tasks

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.TaskItemBinding
import phovdewae.sysadminreminder.util.dateTimeToString
import phovdewae.sysadminreminder.util.makeLinearBorder

class TaskAdapter(private val mainActivity: MainActivity):
    RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private val taskList = ArrayList<Task>()

    inner class TaskHolder(item: View): RecyclerView.ViewHolder(item) {

        private val binding = TaskItemBinding.bind(item)

        fun bind(task: Task) = with(binding) {
            tvId.text = task.id.toString()
            tvDescription.text = task.description
            tvExecutionTime.text = dateTimeToString(task.executionTime)
            tvPriority.text = task.priority

            makeLinearBorder(cvTaskField)
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
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(taskList[position])

        holder.itemView.setOnLongClickListener {
            val popupMenu = PopupMenu(mainActivity, it, Gravity.END)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_task_popup -> mainActivity.cardViewActivity
                        .onChange(
                            taskList[position],
                            mainActivity.binding,
                            mainActivity,
                            mainActivity.bottomNavigationViewActivity,
                            this
                        )
                    R.id.delete_task_popup -> deleteTask(position)
                }
                true
            }
            popupMenu.show()
            true
        }
    }

    fun addTask(task: Task) {
        taskList.add(task)
        notifyItemInserted(taskList.indexOf(task))
    }

    fun editTask(task: Task) {
        notifyItemChanged(taskList.indexOf(taskList.find { it.id == task.id }))
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun deleteTask(position: Int) {
        taskList.removeAt(position)
        notifyItemRemoved(position)
        notifyDataSetChanged()
    }
}