package phovdewae.sysadminreminder.view_activities

import com.google.android.material.bottomnavigation.BottomNavigationView
import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivityMainBinding
import phovdewae.sysadminreminder.tasks.TaskAdapter
import phovdewae.sysadminreminder.util.lastState

class BottomNavigationViewActivity(private val bottomNavigationView: BottomNavigationView) {

    fun onCreate(binding: ActivityMainBinding,
                 mainActivity: MainActivity,
                 cardViewActivity: CardViewActivity,
                 taskAdapter: TaskAdapter) {
        changeMainActivityNameOrAddTask(binding,
            mainActivity,
            cardViewActivity,
            taskAdapter,
            bottomNavigationView.selectedItemId)

        bottomNavigationView.setOnItemSelectedListener {
            changeMainActivityNameOrAddTask(binding,
                mainActivity,
                cardViewActivity,
                taskAdapter,
                it.itemId)
            true
        }
    }

    fun changeMainActivityNameOrAddTask(binding: ActivityMainBinding,
                                                mainActivity: MainActivity,
                                                cardViewActivity: CardViewActivity,
                                                taskAdapter: TaskAdapter,
                                                itemId: Int) {
        when (itemId) {
            R.id.tasks -> {
                mainActivity.title = mainActivity.getString(R.string.tasks_name)
                lastState = R.id.tasks
            }
            R.id.new_task -> {
                cardViewActivity.onCreate(binding, mainActivity, this, taskAdapter)
                /*binding.apply {
                    rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvMain.adapter = taskAdapter
                    taskAdapter.addTask(generateTask())
                }*/
            }
            R.id.tasks_history -> {
                mainActivity.title = mainActivity.getString(R.string.tasks_history_name)
                lastState = R.id.tasks_history
            }
        }
    }
}