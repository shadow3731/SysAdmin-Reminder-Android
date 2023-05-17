package phovdewae.sysadminreminder.tasks

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.TaskItemBinding
import phovdewae.sysadminreminder.util.dateTimeToString
import phovdewae.sysadminreminder.util.makeLinearBorder

class TaskAdapter(private val context: Context): RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private val taskList = ArrayList<Task>()

    class TaskHolder(item: View): RecyclerView.ViewHolder(item) {

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
            val popupMenu = PopupMenu(context, it, Gravity.END)
            popupMenu.inflate(R.menu.popup_menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.edit_task_popup -> Toast.makeText(context, R.string.edit_task_menu, Toast.LENGTH_SHORT).show()
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

    private fun deleteTask(position: Int) {
        taskList.removeAt(position)
        notifyItemRemoved(position)
    }
}