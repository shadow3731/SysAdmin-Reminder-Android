package phovdewae.sysadminreminder.view_activities

import android.content.Context
import android.graphics.Color
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
import phovdewae.sysadminreminder.util.disable
import phovdewae.sysadminreminder.util.enable
import phovdewae.sysadminreminder.util.getPriorities
import phovdewae.sysadminreminder.util.isValid
import phovdewae.sysadminreminder.util.prepareForDateTime
import phovdewae.sysadminreminder.util.move
import phovdewae.sysadminreminder.util.stringToDateTime

class CardViewActivity(private val cardView: CardView) {

    class SpinnerAdapter(context: Context, items: Array<String>):
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

    class SpinnerOnItemSelectedListener(default: String): AdapterView.OnItemSelectedListener {

        var selectedPriority = default

        override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
            selectedPriority = parent.getItemAtPosition(position).toString()
        }

        override fun onNothingSelected(parent: AdapterView<*>) {
            selectedPriority = parent.getItemAtPosition(0).toString()
        }

    }

    fun onCreate(binding: ActivityMainBinding, mainActivity: MainActivity, taskAdapter: TaskAdapter) {
        cardView.visibility = View.VISIBLE


        binding.apply {
            clInnerMain.disable()
            bnvMain.disable()

            etNewTaskExecDate.prepareForDateTime(mainActivity, true)
            etNewTaskExecTime.prepareForDateTime(mainActivity, false)

            val spinnerAdapter = SpinnerAdapter(mainActivity, getPriorities(mainActivity))
            sNewTaskPrior.adapter = spinnerAdapter
            val spinnerListener = SpinnerOnItemSelectedListener(mainActivity.getString(R.string.priority_little))
            sNewTaskPrior.onItemSelectedListener = spinnerListener

            bNewTaskCancel.setOnClickListener {
                close(this, mainActivity)
                bnvMain.move(false, mainActivity)
            }

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

                    close(this, mainActivity)
                    bnvMain.move(true, mainActivity)
                } else {
                    Toast.makeText(mainActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun close(binding: ActivityMainBinding, mainActivity: MainActivity) {
        binding.apply {
            cardView.visibility = View.GONE

            etNewTaskDesc.text.clear()
            etNewTaskExecDate.text.clear()
            etNewTaskExecTime.text.clear()

            clInnerMain.enable()
            bnvMain.enable(mainActivity)
        }
    }
}