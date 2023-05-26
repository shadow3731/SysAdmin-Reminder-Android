package phovdewae.sysadminreminder.view_activities

import phovdewae.sysadminreminder.MainActivity
import phovdewae.sysadminreminder.tasks.TaskAdapter
import phovdewae.sysadminreminder.tasks.TaskCloud
import phovdewae.sysadminreminder.util.lastState
import com.google.android.material.bottomnavigation.BottomNavigationView
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivityMainBinding

class BottomNavigationViewActivity(private val bottomNavigationView: BottomNavigationView) {

    fun onCreate(
        binding: ActivityMainBinding,
        mainActivity: MainActivity,
        cardViewActivity: CardViewActivity,
        recyclerViewActivity: RecyclerViewActivity,
        taskAdapter: TaskAdapter,
        taskCloud: TaskCloud
    ) {
        changeMainActivityNameOrAddTask(
            binding,
            mainActivity,
            cardViewActivity,
            recyclerViewActivity,
            taskAdapter,
            taskCloud,
            bottomNavigationView.selectedItemId
        )

        bottomNavigationView.setOnItemSelectedListener {
            changeMainActivityNameOrAddTask(
                binding,
                mainActivity,
                cardViewActivity,
                recyclerViewActivity,
                taskAdapter,
                taskCloud,
                it.itemId
            )
            true
        }
    }

    fun changeMainActivityNameOrAddTask(
        binding: ActivityMainBinding,
        mainActivity: MainActivity,
        cardViewActivity: CardViewActivity,
        recyclerViewActivity: RecyclerViewActivity,
        taskAdapter: TaskAdapter,
        taskCloud: TaskCloud,
        itemId: Int
    ) {
        when (itemId) {
            R.id.tasks -> {
                lastState = R.id.tasks
                mainActivity.title = mainActivity.getString(R.string.tasks_name)
                recyclerViewActivity.showTasks(mainActivity, taskAdapter, taskCloud)
            }
            R.id.new_task -> {
                cardViewActivity.onCreate(
                    binding,
                    mainActivity,
                    this,
                    recyclerViewActivity,
                    taskAdapter,
                    taskCloud
                )
            }
            R.id.tasks_history -> {
                lastState = R.id.tasks_history
                mainActivity.title = mainActivity.getString(R.string.tasks_history_name)
                recyclerViewActivity.showTasks(mainActivity, taskAdapter, taskCloud)
            }
        }
    }
}