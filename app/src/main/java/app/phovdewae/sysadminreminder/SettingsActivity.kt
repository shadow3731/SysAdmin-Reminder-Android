package app.phovdewae.sysadminreminder

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import app.phovdewae.sysadminreminder.settings.SettingsConfigurator
import app.phovdewae.sysadminreminder.util.settings
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var settingsConfigurator: SettingsConfigurator


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        settingsConfigurator = SettingsConfigurator()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle(R.string.settings)

        settingsConfigurator.displaySettings(binding, settings)
        settingsConfigurator.listenToSettings(binding, this)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        return true
    }
}