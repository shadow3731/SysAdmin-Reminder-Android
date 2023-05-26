package phovdewae.sysadminreminder

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import phovdewae.sysadminreminder.about_app.DeveloperConnection
import phovdewae.sysadminreminder.databinding.ActivityAboutAppBinding

class AboutAppActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAboutAppBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAboutAppBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setTitle(R.string.about_app_button)

        binding.apply {
            val textForWinApp = "${getString(R.string.about_app_windows)} " +
                    getString(R.string.about_app_developing)
            tvWinApp.text = textForWinApp

            ibDevGmailAboutApp.setOnClickListener {
                DeveloperConnection().sendToMail(this@AboutAppActivity, binding)
            }

            ibDevVkAboutApp.setOnClickListener {
                DeveloperConnection().sendToPage(
                    this@AboutAppActivity,
                    binding,
                    getString(R.string.about_app_dev_vkontakte)
                )
            }

            ibDevLinAboutApp.setOnClickListener {
                DeveloperConnection().sendToPage(
                    this@AboutAppActivity,
                    binding,
                    getString(R.string.about_app_dev_linkedin)
                )
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return true
    }
}