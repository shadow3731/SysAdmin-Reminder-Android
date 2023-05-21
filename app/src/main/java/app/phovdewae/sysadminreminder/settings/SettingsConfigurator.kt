package app.phovdewae.sysadminreminder.settings

import android.content.Context
import android.graphics.Color
import app.phovdewae.sysadminreminder.timers.TaskTimer
import app.phovdewae.sysadminreminder.timers.TaskTimerPerformer

class SettingsConfigurator {

    fun loadSettings(
        context: Context,
        taskTimerPerformer: TaskTimerPerformer,
        toLoadDefault: Boolean
    ) {
        val settingsCloud = SettingsCloud()
        val settings = settingsCloud.load(context, toLoadDefault)

        val taskTimers = ArrayList<TaskTimer>()
        taskTimers.add(TaskTimer(
            "Yellow",
            (settings!!.timerYellowValue!! * 60000).toLong(),
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
}