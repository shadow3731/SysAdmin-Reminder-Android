package phovdewae.sysadminreminder.view_activities

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivityMainBinding
import phovdewae.sysadminreminder.tasks.Task
import phovdewae.sysadminreminder.tasks.TaskAdapter
import phovdewae.sysadminreminder.util.counter
import phovdewae.sysadminreminder.util.dateToString
import phovdewae.sysadminreminder.util.definePriorityId
import phovdewae.sysadminreminder.util.disable
import phovdewae.sysadminreminder.util.enable
import phovdewae.sysadminreminder.util.getPriorities
import phovdewae.sysadminreminder.util.hideKeyboard
import phovdewae.sysadminreminder.util.isValid
import phovdewae.sysadminreminder.util.prepareForDateTime
import phovdewae.sysadminreminder.util.move
import phovdewae.sysadminreminder.util.stringToDateTime
import phovdewae.sysadminreminder.util.timeToString

class CardViewActivity(private val cardView: CardView) {

    inner class SpinnerAdapter(context: Context, items: Array<String>):
        ArrayAdapter<String>(context,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            items) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getView(position, convertView, parent)
            view.background = ContextCompat.getDrawable(context, R.drawable.spinner_background)
            (view as TextView).setTextColor(Color.BLACK)
            return view
        }

        override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
            val view = super.getDropDownView(position, convertView, parent)
            view.setBackgroundColor(Color.WHITE)
            (view as TextView).setTextColor(Color.BLACK)
            return view
        }
    }

    inner class SpinnerOnItemSelectedListener: AdapterView.OnItemSelectedListener {

        lateinit var selectedPriority: String

        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            selectedPriority = parent.getItemAtPosition(position).toString()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            selectedPriority = parent.getItemAtPosition(0).toString()
        }

    }

    fun onCreate(
        binding: ActivityMainBinding,
        mainActivity: MainActivity,
        bottomNavigationViewActivity: BottomNavigationViewActivity,
        taskAdapter: TaskAdapter
    ) {
        open(binding, mainActivity, bottomNavigationViewActivity, taskAdapter)

        binding.apply {
            tvNewTaskTitle.text = mainActivity.getString(R.string.new_task_name)

            val spinnerListener = SpinnerOnItemSelectedListener()
            sNewTaskPrior.onItemSelectedListener = spinnerListener

            bNewTaskAdd.text = mainActivity.getText(R.string.add_new_task)

            bNewTaskAdd.setOnClickListener {
                if (isValid(etNewTaskDesc.text.toString(),
                        etNewTaskExecDate.text.toString(),
                        etNewTaskExecTime.text.toString())) {
                    val task = Task((++counter).toLong(),
                        etNewTaskDesc.text.toString(),
                        stringToDateTime(etNewTaskExecDate.text.toString(), etNewTaskExecTime.text.toString()),
                        spinnerListener.selectedPriority
                    )
                    rvMain.layoutManager = LinearLayoutManager(mainActivity)
                    rvMain.adapter = taskAdapter
                    taskAdapter.addTask(task)

                    close(this, mainActivity, bottomNavigationViewActivity, taskAdapter)
                    bnvMain.move(true, mainActivity)
                } else {
                    Toast.makeText(mainActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun onChange(
        task: Task,
        binding: ActivityMainBinding,
        mainActivity: MainActivity,
        bottomNavigationViewActivity: BottomNavigationViewActivity,
        taskAdapter: TaskAdapter
    ) {
        open(binding, mainActivity, bottomNavigationViewActivity, taskAdapter)

        binding.apply {
            tvNewTaskTitle.text = mainActivity.getString(R.string.edit_task_name)

            etNewTaskDesc.text = Editable.Factory.getInstance()
                .newEditable(task.description)
            etNewTaskExecDate.text = Editable.Factory.getInstance()
                .newEditable(dateToString(task.executionTime))
            etNewTaskExecTime.text = Editable.Factory.getInstance()
                .newEditable(timeToString(task.executionTime))

            val spinnerListener = SpinnerOnItemSelectedListener()
            sNewTaskPrior.onItemSelectedListener = spinnerListener
            sNewTaskPrior.setSelection(definePriorityId(mainActivity, task.priority))

            bNewTaskAdd.text = mainActivity.getText(R.string.edit_task)

            bNewTaskAdd.setOnClickListener {
                if (isValid(etNewTaskDesc.text.toString(),
                        etNewTaskExecDate.text.toString(),
                        etNewTaskExecTime.text.toString())
                ) {
                    task.description = etNewTaskDesc.text.toString()
                    task.executionTime = stringToDateTime(
                        etNewTaskExecDate.text.toString(),
                        etNewTaskExecTime.text.toString()
                    )
                    task.priority = spinnerListener.selectedPriority

                    rvMain.layoutManager = LinearLayoutManager(mainActivity)
                    rvMain.adapter = taskAdapter
                    taskAdapter.editTask(task)

                    close(this, mainActivity, bottomNavigationViewActivity, taskAdapter)
                    bnvMain.move(true, mainActivity)
                } else {
                    Toast.makeText(mainActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun open(
        binding: ActivityMainBinding,
        mainActivity: MainActivity,
        bottomNavigationViewActivity: BottomNavigationViewActivity,
        taskAdapter: TaskAdapter
    ) {
        cardView.visibility = View.VISIBLE

        binding.apply {
            clInnerMain.disable()
            rvMain.disable(taskAdapter)
            bnvMain.disable()

            etNewTaskExecDate.prepareForDateTime(mainActivity, true)
            etNewTaskExecTime.prepareForDateTime(mainActivity, false)

            val spinnerAdapter = SpinnerAdapter(mainActivity, getPriorities(mainActivity))
            sNewTaskPrior.adapter = spinnerAdapter

            bNewTaskCancel.setOnClickListener {
                close(this, mainActivity, bottomNavigationViewActivity, taskAdapter)
                bnvMain.move(false, mainActivity)
            }
        }
    }

    private fun close(
        binding: ActivityMainBinding,
        mainActivity: MainActivity,
        bottomNavigationViewActivity: BottomNavigationViewActivity,
        taskAdapter: TaskAdapter
    ) {
        binding.apply {
            cardView.visibility = View.GONE
            cardView.hideKeyboard(mainActivity)

            etNewTaskDesc.text.clear()
            etNewTaskExecDate.text.clear()
            etNewTaskExecTime.text.clear()

            clInnerMain.enable()
            rvMain.enable(taskAdapter)
            bnvMain.enable(
                bottomNavigationViewActivity,
                binding,
                mainActivity,
                this@CardViewActivity,
                taskAdapter)
        }
    }
}