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
import phovdewae.sysadminreminder.tasks.Task
import phovdewae.sysadminreminder.tasks.TaskAdapter
import phovdewae.sysadminreminder.tasks.TaskCloud
import phovdewae.sysadminreminder.util.counter
import phovdewae.sysadminreminder.util.dateToString
import phovdewae.sysadminreminder.util.definePriorityId
import phovdewae.sysadminreminder.util.disable
import phovdewae.sysadminreminder.util.enable
import phovdewae.sysadminreminder.util.hideKeyboard
import phovdewae.sysadminreminder.util.isValid
import phovdewae.sysadminreminder.util.move
import phovdewae.sysadminreminder.util.prepareForDateTime
import phovdewae.sysadminreminder.util.stringToDateTime
import phovdewae.sysadminreminder.util.timeToString
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivityMainBinding
import phovdewae.sysadminreminder.util.defineStatusId
import phovdewae.sysadminreminder.util.priorityList

class CardViewActivity(private val cardView: CardView) {

    inner class SpinnerAdapter(context: Context):
        ArrayAdapter<String>(
            context,
            androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
            priorityList
        ) {

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

        var selectedPriorityId = 0

        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            val selectedPriority = parent.getItemAtPosition(position).toString()
            selectedPriorityId = definePriorityId(selectedPriority)
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            val selectedPriority = parent.getItemAtPosition(0).toString()
            selectedPriorityId - definePriorityId(selectedPriority)
        }

    }

    fun onCreate(
        binding: ActivityMainBinding,
        mainActivity: MainActivity,
        bottomNavigationViewActivity: BottomNavigationViewActivity,
        recyclerViewActivity: RecyclerViewActivity,
        taskAdapter: TaskAdapter,
        taskCloud: TaskCloud
    ) {
        open(
            binding,
            mainActivity,
            bottomNavigationViewActivity,
            recyclerViewActivity,
            taskAdapter,
            taskCloud
        )

        binding.apply {
            tvNewTaskTitle.text = mainActivity.getString(R.string.new_task_name)

            val spinnerListener = SpinnerOnItemSelectedListener()
            sTaskPrior.onItemSelectedListener = spinnerListener

            bTaskAddEdit.text = mainActivity.getText(R.string.add_new_task)

            bTaskAddEdit.setOnClickListener {
                if (isValid(
                        etTaskDesc.text.toString(),
                        etTaskExecDate.text.toString(),
                        etTaskExecTime.text.toString()
                    )
                ) {
                    val task = Task((++counter).toLong(),
                        etTaskDesc.text.toString(),
                        stringToDateTime(
                            etTaskExecDate.text.toString(),
                            etTaskExecTime.text.toString()
                        ),
                        spinnerListener.selectedPriorityId,
                        defineStatusId(mainActivity.getString(R.string.status_active))
                    )
                    rvMain.layoutManager = LinearLayoutManager(mainActivity)
                    rvMain.adapter = taskAdapter
                    taskAdapter.addTask(task)

                    close(
                        this,
                        mainActivity,
                        bottomNavigationViewActivity,
                        recyclerViewActivity,
                        taskAdapter,
                        taskCloud
                    )
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
        recyclerViewActivity: RecyclerViewActivity,
        taskAdapter: TaskAdapter,
        taskCloud: TaskCloud
    ) {
        open(
            binding,
            mainActivity,
            bottomNavigationViewActivity,
            recyclerViewActivity,
            taskAdapter,
            taskCloud
        )

        binding.apply {
            tvNewTaskTitle.text = mainActivity.getString(R.string.edit_task_name)

            etTaskDesc.text = Editable.Factory.getInstance()
                .newEditable(task.description)
            if (task.executionTime != null) {
                etTaskExecDate.text = Editable.Factory.getInstance()
                    .newEditable(dateToString(task.executionTime!!))
                etTaskExecTime.text = Editable.Factory.getInstance()
                    .newEditable(timeToString(task.executionTime!!))
            }

            val spinnerListener = SpinnerOnItemSelectedListener()
            sTaskPrior.onItemSelectedListener = spinnerListener
            if (task.priorityId != null) sTaskPrior.setSelection(task.priorityId!!)

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
                    task.priorityId = spinnerListener.selectedPriorityId

                    rvMain.layoutManager = LinearLayoutManager(mainActivity)
                    rvMain.adapter = taskAdapter
                    taskAdapter.editTask(task)

                    close(
                        this,
                        mainActivity,
                        bottomNavigationViewActivity,
                        recyclerViewActivity,
                        taskAdapter,
                        taskCloud
                    )
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
        recyclerViewActivity: RecyclerViewActivity,
        taskAdapter: TaskAdapter,
        taskCloud: TaskCloud
    ) {
        cardView.visibility = View.VISIBLE

        binding.apply {
            clInnerMain.disable(false)
            rvMain.disable(false, taskAdapter)
            bnvMain.disable()

            etTaskExecDate.prepareForDateTime(mainActivity, true)
            etTaskExecTime.prepareForDateTime(mainActivity, false)

            val spinnerAdapter = SpinnerAdapter(mainActivity)
            sTaskPrior.adapter = spinnerAdapter

            bTaskCancel.setOnClickListener {
                close(
                    this,
                    mainActivity,
                    bottomNavigationViewActivity,
                    recyclerViewActivity,
                    taskAdapter,
                    taskCloud
                )
                bnvMain.move(false, mainActivity)
            }
        }
    }

    private fun close(
        binding: ActivityMainBinding,
        mainActivity: MainActivity,
        bottomNavigationViewActivity: BottomNavigationViewActivity,
        recyclerViewActivity: RecyclerViewActivity,
        taskAdapter: TaskAdapter,
        taskCloud: TaskCloud
    ) {
        binding.apply {
            cardView.visibility = View.GONE
            cardView.hideKeyboard(mainActivity)

            etTaskDesc.text.clear()
            etTaskExecDate.text.clear()
            etTaskExecTime.text.clear()

            clInnerMain.enable(false, mainActivity)
            rvMain.enable(false, mainActivity, taskAdapter)
            bnvMain.enable()
            bnvMain.setOnItemSelectedListener {
                bottomNavigationViewActivity
                    .changeMainActivityNameOrAddTask(
                        binding,
                        mainActivity,
                        this@CardViewActivity,
                        recyclerViewActivity,
                        taskAdapter,
                        taskCloud,
                        it.itemId)
                true
            }
        }
    }
}