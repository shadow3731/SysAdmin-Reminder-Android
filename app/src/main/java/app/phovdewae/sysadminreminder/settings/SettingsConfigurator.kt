package app.phovdewae.sysadminreminder.settings

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.util.Base64
import android.widget.Toast
import app.phovdewae.sysadminreminder.SettingsActivity
import app.phovdewae.sysadminreminder.timers.TaskTimer
import app.phovdewae.sysadminreminder.timers.TaskTimerPerformer
import app.phovdewae.sysadminreminder.util.disable
import app.phovdewae.sysadminreminder.util.enable
import app.phovdewae.sysadminreminder.util.settings
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivitySettingsBinding

class SettingsConfigurator {

    private fun saveSettings(settingsActivity: SettingsActivity) {
        val settingsCloud = SettingsCloud()
        settingsCloud.save(settingsActivity, settings)
    }

    fun loadSettings(
        context: Context,
        taskTimerPerformer: TaskTimerPerformer,
        toLoadDefault: Boolean = false
    ) {
        val settingsCloud = SettingsCloud()
        settings = settingsCloud.load(context, toLoadDefault)!!

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

    fun displaySettings(binding: ActivitySettingsBinding) {
        binding.apply {
            swDbConnSettings.isChecked = settings.databaseConnectionEnabled!!
            etDbAddress.text = Editable.Factory.getInstance()
                .newEditable(settings.databaseAddress!!)
            etDbPort.text = Editable.Factory.getInstance()
                .newEditable(settings.databasePort!!)
            etDbName.text = Editable.Factory.getInstance()
                .newEditable(settings.databaseName!!)
            etDbUsername.text = Editable.Factory.getInstance()
                .newEditable(settings.databaseUsername!!)
            etDbPassword.text = Editable.Factory.getInstance()
                .newEditable(Base64.decode(settings.databasePassword!!, Base64.DEFAULT).toString())
            etDbSyncTime.text = Editable.Factory.getInstance()
                .newEditable(settings.databaseSyncTime!!.toString())
            swTimersYellow.isChecked = settings.timerYellowEnabled!!
            etTimersYellow.text = Editable.Factory.getInstance()
                .newEditable(settings.timerYellowValue!!.toString())
            swTimersOrange.isChecked = settings.timerOrangeEnabled!!
            etTimersOrange.text = Editable.Factory.getInstance()
                .newEditable(settings.timerOrangeValue!!.toString())
            swTimersRed.isChecked = settings.timerRedEnabled!!
            etTimersRed.text = Editable.Factory.getInstance()
                .newEditable(settings.timerRedValue!!.toString())
            swTimersGray.isChecked = settings.timerGrayEnabled!!
            etTimersGray.text = Editable.Factory.getInstance()
                .newEditable(settings.timerGrayValue!!.toString())

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

    fun listenToSettings(
        settingsActivity: SettingsActivity,
        binding: ActivitySettingsBinding,
        taskTimerPerformer: TaskTimerPerformer
    ) {
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
                    etDbSyncTime.toString().toIntOrNull() != null
                    && etDbSyncTime.toString().toInt() < 1
                    && etTimersYellow.toString().toIntOrNull() != null
                    && etTimersOrange.toString().toIntOrNull() != null
                    && etTimersGray.toString().toIntOrNull() != null
                ) {
                    settings.databaseConnectionEnabled = swDbConnSettings.isChecked
                    settings.databaseAddress = if (etDbAddress.toString() == "") "null" else etDbAddress.toString()
                    settings.databasePort = if (etDbPort.toString() == "") "null" else etDbPort.toString()
                    settings.databaseName = if (etDbName.toString() == "") "null" else etDbName.toString()
                    settings.databaseUsername = if (etDbUsername.toString() == "") "null" else etDbUsername.toString()
                    settings.databasePassword = if (etDbPassword.toString() == "") null
                    else Base64.encode(etDbPassword.toString().toByteArray(), Base64.DEFAULT)
                    settings.databaseSyncTime = etDbSyncTime.toString().toInt()
                    settings.timerYellowEnabled = swTimersYellow.isChecked
                    settings.timerYellowValue = etTimersYellow.toString().toInt()
                    settings.timerOrangeEnabled = swTimersOrange.isChecked
                    settings.timerOrangeValue = etTimersOrange.toString().toInt()
                    settings.timerRedEnabled = swTimersRed.isChecked
                    settings.timerRedValue = etTimersRed.toString().toInt()
                    settings.timerGrayEnabled = swTimersGray.isChecked
                    settings.timerGrayValue = etTimersGray.toString().toInt()

                    saveSettings(settingsActivity)
                    loadSettings(settingsActivity, taskTimerPerformer)
                    settingsActivity.finish()
                } else {
                    Toast.makeText(settingsActivity, R.string.invalid_data, Toast.LENGTH_SHORT).show()
                }
            }

            bCancelSettings.setOnClickListener { settingsActivity.finish() }
        }
    }
}