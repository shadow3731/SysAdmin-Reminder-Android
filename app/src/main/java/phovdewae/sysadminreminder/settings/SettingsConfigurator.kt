package phovdewae.sysadminreminder.settings

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.text.Editable
import android.util.Base64
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import phovdewae.sysadminreminder.AboutAppActivity
import phovdewae.sysadminreminder.SettingsActivity
import phovdewae.sysadminreminder.timers.TaskTimer
import phovdewae.sysadminreminder.util.disable
import phovdewae.sysadminreminder.util.enable
import phovdewae.sysadminreminder.util.settings
import phovdewae.sysadminreminder.util.taskTimerPerformer
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivitySettingsBinding

class SettingsConfigurator {

    private fun saveSettings(settingsActivity: SettingsActivity): Boolean {
        return SettingsCloud().save(settingsActivity, settings)
    }

    fun loadSettings(context: Context, toLoadDefault: Boolean = false): Settings {
        return SettingsCloud().load(context, toLoadDefault)!!
    }

    fun applySettings() {
        val taskTimers = ArrayList<TaskTimer>()
        taskTimers.add(
            TaskTimer(
            "Yellow",
            (settings.timerYellowValue!! * 60000).toLong(),
            Color.YELLOW,
            settings.timerYellowEnabled!!
        )
        )
        taskTimers.add(
            TaskTimer(
            "Orange",
            (settings.timerOrangeValue!! * 60000).toLong(),
            Color.rgb(225, 165, 0),
            settings.timerOrangeEnabled!!
        )
        )
        taskTimers.add(
            TaskTimer(
            "Red",
            (settings.timerRedValue!! * 60000).toLong(),
            Color.RED,
            settings.timerRedEnabled!!
        )
        )
        taskTimers.add(
            TaskTimer(
            "Gray",
            (settings.timerGrayValue!! * 60000).toLong(),
            Color.GRAY,
            settings.timerGrayEnabled!!
        )
        )

        taskTimerPerformer.taskTimerLists = taskTimers
        taskTimerPerformer.onChangeTaskTimersDuration()
    }

    fun displaySettings(binding: ActivitySettingsBinding, displayableSettings: Settings) {
        binding.apply {
            swDbConnSettings.isChecked = displayableSettings.databaseConnectionEnabled!!
            etDbAddress.text = Editable.Factory.getInstance()
                .newEditable(displayableSettings.databaseAddress!!)
            etDbPort.text = Editable.Factory.getInstance()
                .newEditable(displayableSettings.databasePort!!)
            etDbName.text = Editable.Factory.getInstance()
                .newEditable(displayableSettings.databaseName!!)
            etDbUsername.text = Editable.Factory.getInstance()
                .newEditable(displayableSettings.databaseUsername!!)
            if (displayableSettings.databasePassword != null)
                etDbPassword.text = Editable.Factory.getInstance()
                    .newEditable(Base64.decode(displayableSettings.databasePassword!!, Base64.DEFAULT)
                        .toString())
            etDbSyncTime.text = Editable.Factory.getInstance()
                .newEditable(displayableSettings.databaseSyncTime!!.toString())
            swTimersYellow.isChecked = displayableSettings.timerYellowEnabled!!
            etTimersYellow.text = Editable.Factory.getInstance()
                .newEditable(displayableSettings.timerYellowValue!!.toString())
            swTimersOrange.isChecked = displayableSettings.timerOrangeEnabled!!
            etTimersOrange.text = Editable.Factory.getInstance()
                .newEditable(displayableSettings.timerOrangeValue!!.toString())
            swTimersRed.isChecked = displayableSettings.timerRedEnabled!!
            etTimersRed.text = Editable.Factory.getInstance()
                .newEditable(displayableSettings.timerRedValue!!.toString())
            swTimersGray.isChecked = displayableSettings.timerGrayEnabled!!
            etTimersGray.text = Editable.Factory.getInstance()
                .newEditable(displayableSettings.timerGrayValue!!.toString())

            if (!swDbConnSettings.isChecked) {
                etDbAddress.disable(true)
                etDbPort.disable(true)
                etDbName.disable(true)
                etDbUsername.disable(true)
                etDbPassword.disable(true)
                etDbSyncTime.disable(true)
                bExportTasks.disable(true)
                bImportTasks.disable(true)
            }
            if (!swTimersYellow.isChecked) etTimersYellow.disable(true)
            if (!swTimersOrange.isChecked) etTimersOrange.disable(true)
            if (!swTimersRed.isChecked) etTimersRed.disable(true)
            if (!swTimersGray.isChecked) etTimersGray.disable(true)
        }
    }

    fun listenToSettings(
        binding: ActivitySettingsBinding,
        settingsActivity: SettingsActivity
    ) {
        binding.apply {
            swDbConnSettings.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    etDbAddress.enable(true, settingsActivity)
                    etDbPort.enable(true, settingsActivity)
                    etDbName.enable(true, settingsActivity)
                    etDbUsername.enable(true, settingsActivity)
                    etDbPassword.enable(true, settingsActivity)
                    etDbSyncTime.enable(true, settingsActivity)
                    bExportTasks.enable(true, settingsActivity)
                    bImportTasks.enable(true, settingsActivity)
                } else {
                    etDbAddress.disable(true)
                    etDbPort.disable(true)
                    etDbName.disable(true)
                    etDbUsername.disable(true)
                    etDbPassword.disable(true)
                    etDbSyncTime.disable(true)
                    bExportTasks.disable(true)
                    bImportTasks.disable(true)
                }
            }

            swTimersYellow.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) etTimersYellow.enable(true, settingsActivity)
                else etTimersYellow.disable(true)
            }

            swTimersOrange.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) etTimersOrange.enable(true, settingsActivity)
                else etTimersOrange.disable(true)
            }

            swTimersRed.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) etTimersRed.enable(true, settingsActivity)
                else etTimersRed.disable(true)
            }

            swTimersGray.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) etTimersGray.enable(true, settingsActivity)
                else etTimersGray.disable(true)
            }

            ibHelpDbSync.setOnClickListener {
                listenToHelp(
                    binding,
                    settingsActivity.getString(R.string.db_sync_help),
                    settingsActivity
                )
            }

            ibHelpTimers.setOnClickListener {
                listenToHelp(
                    binding,
                    settingsActivity.getString(R.string.db_timers_help),
                    settingsActivity
                )
            }

            bSaveSettings.setOnClickListener {
                if (
                    etDbSyncTime.text.toString().toIntOrNull() != null
                    && etDbSyncTime.text.toString().toInt() > 0
                    && etTimersYellow.text.toString().toIntOrNull() != null
                    && etTimersOrange.text.toString().toIntOrNull() != null
                    && etTimersRed.text.toString().toIntOrNull() != null
                    && etTimersGray.text.toString().toIntOrNull() != null
                ) {
                    settings.databaseConnectionEnabled = swDbConnSettings.isChecked
                    settings.databaseAddress = if (etDbAddress.text.isNullOrEmpty()) ""
                    else etDbAddress.text.toString()
                    settings.databasePort = if (etDbPort.text.isNullOrEmpty()) ""
                    else etDbPort.text.toString()
                    settings.databaseName = if (etDbName.text.isNullOrEmpty()) ""
                    else etDbName.text.toString()
                    settings.databaseUsername = if (etDbUsername.text.isNullOrEmpty()) ""
                    else etDbUsername.text.toString()
                    settings.databasePassword = if (etDbPassword.text.isNullOrEmpty()) null
                    else Base64.encode(etDbPassword.text.toString().toByteArray(), Base64.DEFAULT)
                    settings.databaseSyncTime = etDbSyncTime.text.toString().toInt()
                    settings.timerYellowEnabled = swTimersYellow.isChecked
                    settings.timerYellowValue = etTimersYellow.text.toString().toInt()
                    settings.timerOrangeEnabled = swTimersOrange.isChecked
                    settings.timerOrangeValue = etTimersOrange.text.toString().toInt()
                    settings.timerRedEnabled = swTimersRed.isChecked
                    settings.timerRedValue = etTimersRed.text.toString().toInt()
                    settings.timerGrayEnabled = swTimersGray.isChecked
                    settings.timerGrayValue = etTimersGray.text.toString().toInt()

                    saveSettings(settingsActivity)
                    applySettings()
                    settingsActivity.setResult(Activity.RESULT_OK)
                    settingsActivity.finish()
                } else {
                    Toast.makeText(settingsActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }
            }

            bCancelSettings.setOnClickListener {
                settingsActivity.setResult(Activity.RESULT_CANCELED)
                settingsActivity.finish()
            }

            bResetSettings.setOnClickListener {
                displaySettings(binding, loadSettings(settingsActivity, true))
            }

            bAboutAppSettings.setOnClickListener {
                val intent = Intent(settingsActivity, AboutAppActivity::class.java)
                settingsActivity.startActivity(intent)
            }
        }
    }

    private fun listenToHelp(binding: ActivitySettingsBinding, text: String, context: Context) {
        binding.apply {
            cvHelp.visibility = View.VISIBLE
            tvHelpDesc.text = text
            disableElementsInSettings(svSettings)

            bHelpClose.setOnClickListener {
                cvHelp.visibility = View.GONE
                tvHelpDesc.text = ""
                enableElementsInSettings(svSettings, context)
                displaySettings(binding, settings)
                listenToSettings(binding, context as SettingsActivity)
            }
        }
    }

    private fun enableElementsInSettings(element: View, context: Context) {
        element.enable(false, context)

        if (element is ViewGroup) {
            for (i in 0 until element.childCount) {
                enableElementsInSettings(element.getChildAt(i), context)
            }
        }
    }

    private fun disableElementsInSettings(element: View) {
        element.disable(false)

        if (element is ViewGroup) {
            for (i in 0 until element.childCount) {
                disableElementsInSettings(element.getChildAt(i))
            }
        }
    }
}