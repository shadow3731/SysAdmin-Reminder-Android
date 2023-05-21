package app.phovdewae.sysadminreminder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import app.phovdewae.sysadminreminder.settings.SettingsConfigurator
import app.phovdewae.sysadminreminder.tasks.TaskAdapter
import app.phovdewae.sysadminreminder.tasks.TaskCloud
import app.phovdewae.sysadminreminder.timers.TaskTimerPerformer
import app.phovdewae.sysadminreminder.util.lastState
import app.phovdewae.sysadminreminder.view_activities.BottomNavigationViewActivity
import app.phovdewae.sysadminreminder.view_activities.CardViewActivity
import app.phovdewae.sysadminreminder.view_activities.RecyclerViewActivity
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val taskTimerPerformer = TaskTimerPerformer()
    private val taskCloud = TaskCloud()
    private val taskAdapter = TaskAdapter(this, taskCloud, taskTimerPerformer)
    lateinit var cardViewActivity: CardViewActivity
    lateinit var bottomNavigationViewActivity: BottomNavigationViewActivity
    lateinit var recyclerViewActivity: RecyclerViewActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        SettingsConfigurator().loadSettings(this, taskTimerPerformer, false)

        binding.apply {
            cardViewActivity = CardViewActivity(cvTaskMain)
            bottomNavigationViewActivity = BottomNavigationViewActivity(bnvMain)
            recyclerViewActivity = RecyclerViewActivity(rvMain)

            bottomNavigationViewActivity.onCreate(
                binding,
                this@MainActivity,
                cardViewActivity,
                recyclerViewActivity,
                taskAdapter,
                taskCloud
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.upper_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.refresh -> Toast.makeText(this, R.string.refresh, Toast.LENGTH_SHORT).show()
            R.id.settings -> startActivity(Intent(this, SettingsActivity::class.java))
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("lastState", lastState)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        bottomNavigationViewActivity.changeMainActivityNameOrAddTask(
            binding,
            this,
            cardViewActivity,
            recyclerViewActivity,
            taskAdapter,
            taskCloud,
            binding.bnvMain.selectedItemId
        )

        lastState = savedInstanceState.getInt("lastState")
    }
}