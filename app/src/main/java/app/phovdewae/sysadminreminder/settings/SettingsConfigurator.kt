package app.phovdewae.sysadminreminder.settings

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.util.Base64
import android.util.Log
import android.widget.Toast
import app.phovdewae.sysadminreminder.SettingsActivity
import app.phovdewae.sysadminreminder.timers.TaskTimer
import app.phovdewae.sysadminreminder.util.disable
import app.phovdewae.sysadminreminder.util.enable
import app.phovdewae.sysadminreminder.util.settings
import app.phovdewae.sysadminreminder.util.taskTimerPerformer
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
        taskTimers.add(TaskTimer(
            "Yellow",
            (settings.timerYellowValue!! * 60000).toLong(),
            Color.YELLOW,
            settings.timerYellowEnabled!!
        ))
        taskTimers.add(TaskTimer(
            "Orange",
            (settings.timerOrangeValue!! * 60000).toLong(),
            Color.rgb(225, 165, 0),
            settings.timerOrangeEnabled!!
        ))
        taskTimers.add(TaskTimer(
            "Red",
            (settings.timerRedValue!! * 60000).toLong(),
            Color.RED,
            settings.timerRedEnabled!!
        ))
        taskTimers.add(TaskTimer(
            "Gray",
            (settings.timerGrayValue!! * 60000).toLong(),
            Color.GRAY,
            settings.timerGrayEnabled!!
        ))

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
                etDbAddress.disable()
                etDbPort.disable()
                etDbName.disable()
                etDbUsername.disable()
                etDbPassword.disable()
                etDbSyncTime.disable()
                bUploadTasks.disable()
                bDownloadTasks.disable()
            }
            if (!swTimersYellow.isChecked) etTimersYellow.disable()
            if (!swTimersOrange.isChecked) etTimersOrange.disable()
            if (!swTimersRed.isChecked) etTimersRed.disable()
            if (!swTimersGray.isChecked) etTimersGray.disable()
        }
    }

    fun listenToSettings(binding: ActivitySettingsBinding, settingsActivity: SettingsActivity) {
        binding.apply {
            swDbConnSettings.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    etDbAddress.enable(settingsActivity)
                    etDbPort.enable(settingsActivity)
                    etDbName.enable(settingsActivity)
                    etDbUsername.enable(settingsActivity)
                    etDbPassword.enable(settingsActivity)
                    etDbSyncTime.enable(settingsActivity)
                    bUploadTasks.enable(settingsActivity)
                    bDownloadTasks.enable(settingsActivity)
                } else {
                    etDbAddress.disable()
                    etDbPort.disable()
                    etDbName.disable()
                    etDbUsername.disable()
                    etDbPassword.disable()
                    etDbSyncTime.disable()
                    bUploadTasks.disable()
                    bDownloadTasks.disable()
                }
            }

            swTimersYellow.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) etTimersYellow.enable(settingsActivity) else etTimersYellow.disable()
            }

            swTimersOrange.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) etTimersOrange.enable(settingsActivity) else etTimersOrange.disable()
            }

            swTimersRed.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) etTimersRed.enable(settingsActivity) else etTimersRed.disable()
            }

            swTimersGray.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) etTimersGray.enable(settingsActivity) else etTimersGray.disable()
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

                    Log.d("MyTag", settings.toString())

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
        }
    }
}