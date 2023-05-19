package app.phovdewae.sysadminreminder.view_activities

import app.phovdewae.sysadminreminder.MainActivity
import app.phovdewae.sysadminreminder.tasks.TaskAdapter
import app.phovdewae.sysadminreminder.util.lastState
import com.google.android.material.bottomnavigation.BottomNavigationView
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivityMainBinding

class BottomNavigationViewActivity(private val bottomNavigationView: BottomNavigationView) {

    fun onCreate(binding: ActivityMainBinding,
                 mainActivity: MainActivity,
                 cardViewActivity: CardViewActivity,
                 taskAdapter: TaskAdapter
    ) {
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
                cardViewActivity.onCreate(
                    binding,
                    mainActivity,
                    this,
                    taskAdapter
                )
            }
            R.id.tasks_history -> {
                mainActivity.title = mainActivity.getString(R.string.tasks_history_name)
                lastState = R.id.tasks_history
            }
        }
    }
}