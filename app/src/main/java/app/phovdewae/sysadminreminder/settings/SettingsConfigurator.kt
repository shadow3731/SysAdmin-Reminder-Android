package app.phovdewae.sysadminreminder.settings

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.util.Base64
import app.phovdewae.sysadminreminder.timers.TaskTimer
import app.phovdewae.sysadminreminder.timers.TaskTimerPerformer
import app.phovdewae.sysadminreminder.util.disable
import app.phovdewae.sysadminreminder.util.settings
import phovdewae.sysadminreminder.databinding.ActivitySettingsBinding

class SettingsConfigurator {

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
            }
            if (!swTimersYellow.isChecked) etTimersYellow.disable()
            if (!swTimersOrange.isChecked) etTimersOrange.disable()
            if (!swTimersRed.isChecked) etTimersRed.disable()
            if (!swTimersGray.isChecked) etTimersGray.disable()
        }
    }
}