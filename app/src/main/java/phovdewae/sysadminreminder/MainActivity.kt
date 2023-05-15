package phovdewae.sysadminreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import phovdewae.sysadminreminder.databinding.ActivityMainBinding
import phovdewae.sysadminreminder.tasks.TaskAdapter
import phovdewae.sysadminreminder.view_activities.CardViewActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val taskAdapter = TaskAdapter()
    private lateinit var cardViewActivity: CardViewActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cardViewActivity = CardViewActivity(binding.cvNewTaskMain)

        changeActivityNameOrAddTask(binding.bnvMain.selectedItemId)

        binding.bnvMain.setOnItemSelectedListener {
            changeActivityNameOrAddTask(it.itemId)
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.upper_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> Toast.makeText(this, R.string.refresh, Toast.LENGTH_SHORT).show()
            R.id.settings -> Toast.makeText(this, R.string.settings, Toast.LENGTH_SHORT).show()
        }
        return true
    }

    fun changeActivityNameOrAddTask(itemId: Int) {
        when (itemId) {
            R.id.tasks -> setTitle(R.string.tasks_name)
            R.id.new_task -> {
                cardViewActivity.onCreate(binding, this@MainActivity)
                /*binding.apply {
                    rvMain.layoutManager = LinearLayoutManager(this@MainActivity)
                    rvMain.adapter = taskAdapter
                    taskAdapter.addTask(generateTask())
                }*/
            }
            R.id.tasks_history -> setTitle(R.string.tasks_history_name)
        }
    }
}