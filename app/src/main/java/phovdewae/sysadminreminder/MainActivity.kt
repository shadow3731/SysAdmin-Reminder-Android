package phovdewae.sysadminreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import phovdewae.sysadminreminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeActivityName(resources.getResourceEntryName(binding.bnvMain.selectedItemId))

        binding.bnvMain.setOnItemSelectedListener {
            changeActivityName(resources.getResourceEntryName(it.itemId))
            true
        }
    }

    private fun changeActivityName(item: String) {
        when (item) {
            getString(R.string.tasks_code) -> setTitle(R.string.tasks_name)
            getString(R.string.tasks_history_code) -> setTitle(R.string.tasks_history_name)
        }
    }
}