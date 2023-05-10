package phovdewae.sysadminreminder.tasks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.TaskItemBinding
import phovdewae.sysadminreminder.util.getFormattedDate
import phovdewae.sysadminreminder.util.getLinearBorder

class TaskAdapter: RecyclerView.Adapter<TaskAdapter.TaskHolder>() {

    private val taskList = ArrayList<Task>()

    class TaskHolder(item: View): RecyclerView.ViewHolder(item) {

        private val binding = TaskItemBinding.bind(item)

        fun bind(task: Task) = with(binding) {
            tvId.text = task.id.toString()
            tvDescription.text = task.description
            tvExecutionTime.text = getFormattedDate(task.executionTime)
            tvPriority.text = task.priority

            cvTaskField.background = getLinearBorder()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskHolder {
        return TaskHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.task_item, parent, false))
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onBindViewHolder(holder: TaskHolder, position: Int) {
        holder.bind(taskList[position])
    }

    fun addTask(task: Task) {
        taskList.add(task)
        notifyItemInserted(taskList.indexOf(task))
    }
}