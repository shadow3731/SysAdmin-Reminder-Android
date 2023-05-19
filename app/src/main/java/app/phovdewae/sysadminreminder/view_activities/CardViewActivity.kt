package app.phovdewae.sysadminreminder.view_activities

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
import app.phovdewae.sysadminreminder.MainActivity
import app.phovdewae.sysadminreminder.tasks.Task
import app.phovdewae.sysadminreminder.tasks.TaskAdapter
import app.phovdewae.sysadminreminder.util.counter
import app.phovdewae.sysadminreminder.util.dateToString
import app.phovdewae.sysadminreminder.util.definePriorityId
import app.phovdewae.sysadminreminder.util.disable
import app.phovdewae.sysadminreminder.util.enable
import app.phovdewae.sysadminreminder.util.getPriorities
import app.phovdewae.sysadminreminder.util.hideKeyboard
import app.phovdewae.sysadminreminder.util.isValid
import app.phovdewae.sysadminreminder.util.move
import app.phovdewae.sysadminreminder.util.prepareForDateTime
import app.phovdewae.sysadminreminder.util.stringToDateTime
import app.phovdewae.sysadminreminder.util.timeToString
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivityMainBinding

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
            sTaskPrior.onItemSelectedListener = spinnerListener

            bTaskAddEdit.text = mainActivity.getText(R.string.add_new_task)

            bTaskAddEdit.setOnClickListener {
                if (isValid(etTaskDesc.text.toString(),
                        etTaskExecDate.text.toString(),
                        etTaskExecTime.text.toString())
                ) {
                    val task = Task((++counter).toLong(),
                        etTaskDesc.text.toString(),
                        stringToDateTime(etTaskExecDate.text.toString(), etTaskExecTime.text.toString()),
                        spinnerListener.selectedPriority,
                        mainActivity.getString(R.string.active_task)
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

            etTaskDesc.text = Editable.Factory.getInstance()
                .newEditable(task.description)
            etTaskExecDate.text = Editable.Factory.getInstance()
                .newEditable(dateToString(task.executionTime))
            etTaskExecTime.text = Editable.Factory.getInstance()
                .newEditable(timeToString(task.executionTime))

            val spinnerListener = SpinnerOnItemSelectedListener()
            sTaskPrior.onItemSelectedListener = spinnerListener
            sTaskPrior.setSelection(definePriorityId(mainActivity, task.priority))

            bTaskAddEdit.text = mainActivity.getText(R.string.edit_task)

            bTaskAddEdit.setOnClickListener {
                if (isValid(etTaskDesc.text.toString(),
                        etTaskExecDate.text.toString(),
                        etTaskExecTime.text.toString())
                ) {
                    task.description = etTaskDesc.text.toString()
                    task.executionTime = stringToDateTime(
                        etTaskExecDate.text.toString(),
                        etTaskExecTime.text.toString()
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

            etTaskExecDate.prepareForDateTime(mainActivity, true)
            etTaskExecTime.prepareForDateTime(mainActivity, false)

            val spinnerAdapter = SpinnerAdapter(mainActivity, getPriorities(mainActivity))
            sTaskPrior.adapter = spinnerAdapter

            bTaskCancel.setOnClickListener {
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

            etTaskDesc.text.clear()
            etTaskExecDate.text.clear()
            etTaskExecTime.text.clear()

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