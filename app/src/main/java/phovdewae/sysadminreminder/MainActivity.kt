package phovdewae.sysadminreminder

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import phovdewae.sysadminreminder.notifications.NotificationConfigurator
import phovdewae.sysadminreminder.settings.SettingsConfigurator
import phovdewae.sysadminreminder.tasks.TaskAdapter
import phovdewae.sysadminreminder.tasks.TaskCloud
import phovdewae.sysadminreminder.util.lastState
import phovdewae.sysadminreminder.util.settings
import phovdewae.sysadminreminder.view_activities.BottomNavigationViewActivity
import phovdewae.sysadminreminder.view_activities.CardViewActivity
import phovdewae.sysadminreminder.view_activities.RecyclerViewActivity
import phovdewae.sysadminreminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private val notificationConfigurator = NotificationConfigurator()
    private val taskCloud = TaskCloud()
    private val taskAdapter = TaskAdapter(this, taskCloud, notificationConfigurator)
    lateinit var cardViewActivity: CardViewActivity
    lateinit var bottomNavigationViewActivity: BottomNavigationViewActivity
    lateinit var recyclerViewActivity: RecyclerViewActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        notificationConfigurator.createNotificationChannel(this)

        val settingsConfigurator = SettingsConfigurator()
        settings = settingsConfigurator.loadSettings(this)
        settingsConfigurator.applySettings()

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
            R.id.settings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                resultActivity.launch(intent)
            }
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

    private val resultActivity = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == Activity.RESULT_OK)
            recyclerViewActivity.showTasks(this, taskAdapter, taskCloud)
    }
}