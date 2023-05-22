package app.phovdewae.sysadminreminder

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import app.phovdewae.sysadminreminder.settings.SettingsConfigurator
import app.phovdewae.sysadminreminder.timers.TaskTimerPerformer
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settingsConfigurator: SettingsConfigurator
    private lateinit var taskTimerPerformer: TaskTimerPerformer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsConfigurator = SettingsConfigurator()
        taskTimerPerformer = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent
                .getSerializableExtra("taskTimerPerformer", TaskTimerPerformer::class.java)!!
        } else {
            intent.getSerializableExtra("taskTimerPerformer") as TaskTimerPerformer
        }


        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle(R.string.settings)

        settingsConfigurator.displaySettings(binding)
        settingsConfigurator.listenToSettings(this, binding, taskTimerPerformer)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }
}