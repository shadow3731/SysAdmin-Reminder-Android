package phovdewae.sysadminreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import phovdewae.sysadminreminder.databinding.ActivityMainBinding
import phovdewae.sysadminreminder.tasks.TaskAdapter
import phovdewae.sysadminreminder.util.TaskUtil

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val taskAdapter = TaskAdapter()
    private val taskUtil = TaskUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeActivityNameOrAddTask(resources
            .getResourceEntryName(binding.bnvMain.selectedItemId))

        binding.bnvMain.setOnItemSelectedListener {
            changeActivityNameOrAddTask(resources.getResourceEntryName(it.itemId))
            true
        }
    }

    private fun changeActivityNameOrAddTask(item: String) {
        when (item) {
            getString(R.string.tasks_code) -> setTitle(R.string.tasks_name)
            getString(R.string.new_task_code) -> {
                binding.apply {
                    rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvMain.adapter = taskAdapter
                    taskAdapter.addTask(taskUtil.generateTask())
                }
            }
            getString(R.string.tasks_history_code) -> setTitle(R.string.tasks_history_name)
        }
    }
}