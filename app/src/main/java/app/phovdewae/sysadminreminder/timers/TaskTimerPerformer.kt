package app.phovdewae.sysadminreminder.timers

import android.content.Context
import android.graphics.Color
import androidx.cardview.widget.CardView
import app.phovdewae.sysadminreminder.notifications.ExecutionCodes
import app.phovdewae.sysadminreminder.notifications.NotificationConfigurator
import app.phovdewae.sysadminreminder.tasks.Task
import app.phovdewae.sysadminreminder.util.makeBackground
import java.util.Date
import java.util.Timer
import java.util.TimerTask

class TaskTimerPerformer {

    private val defaultTaskTimerList = arrayListOf(
        TaskTimer("Yellow", 3600000, Color.YELLOW, true),
        TaskTimer("Orange", 1200000, Color.rgb(225, 165, 0), true),
        TaskTimer("Red", 0, Color.RED, true),
        TaskTimer("Gray", -300000, Color.GRAY, true)
    )
    var taskTimerLists = ArrayList(defaultTaskTimerList)
    private var timers = ArrayList<ArrayList<Timer?>>()
    private var cardViews = ArrayList<CardView>()

    fun onChangeTaskTimersDuration() {
        taskTimerLists.sortedByDescending { it.duration }
    }

    fun getColor(timestamp: Date): Int {
        val current = timestamp.time - Date().time
        var colorId = Color.WHITE
        var i = 0

        while (i < taskTimerLists.size) {
            if (taskTimerLists[i].isEnable) {
                if (current > taskTimerLists[i].duration) break
                else colorId = taskTimerLists[i].backgroundColor
            }
            i++
        }

        return colorId
    }

    fun addTimers(
        position: Int?,
        task: Task,
        cardView: CardView,
        context: Context,
        notificationConfigurator: NotificationConfigurator
    ) {
        val current = task.executionTime!!.time - Date().time

        if (position == null) {
            timers.add(ArrayList(taskTimerLists.size))
            cardViews.add(cardView)
        } else timers.add(position, ArrayList(taskTimerLists.size))

        for (i in 0 until taskTimerLists.size) {
            if (taskTimerLists[i].isEnable && current > taskTimerLists[i].duration) {
                val difference = current - taskTimerLists[i].duration
                if (position == null) {
                    timers[timers.size - 1].add(createTimerTask(
                        difference,
                        task.description!!,
                        taskTimerLists[i].backgroundColor,
                        cardView,
                        context,
                        notificationConfigurator
                    ))
                } else {
                    timers[position].add(createTimerTask(
                        difference,
                        task.description!!,
                        taskTimerLists[i].backgroundColor,
                        cardView,
                        context,
                        notificationConfigurator
                    ))
                }
            } else {
                if (position == null) timers[timers.size - 1].add(null)
                else timers[position].add(null)
            }
        }
    }

    fun editTimers(
        position: Int,
        task: Task,
        context: Context,
        notificationConfigurator: NotificationConfigurator
    ) {
        deleteTimers(position, true)
        addTimers(position, task, cardViews[position], context, notificationConfigurator)
    }

    fun deleteTimers(position: Int, forReschedule: Boolean) {
        for (i in 0 until timers[position].size) {
            timers[position][i]?.cancel()
        }

        timers.removeAt(position)
        if (!forReschedule) cardViews.removeAt(position)
    }

    fun deleteAllTimers() {
        for (i in 0 until timers.size) {
            for (j in 0 until timers[i].size) {
                timers[i][j]?.cancel()
            }
        }

        timers.clear()
        cardViews.clear()
    }

    private fun createTimerTask(
        mills: Long,
        description: String,
        color: Int,
        cardView: CardView,
        context: Context,
        notificationConfigurator: NotificationConfigurator
    ): Timer {
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                makeBackground(color, cardView)

                val duration = taskTimerLists.find { it.backgroundColor == color }?.duration
                if (duration != null) {
                    if (duration / 60000 > 0) {
                        notificationConfigurator
                            .createNotification(
                                context,
                                ExecutionCodes.BEFORE_EXECUTION,
                                description,
                                (duration / 60000).toInt()
                            )
                    } else if (duration / 60000 < 0) {
                        notificationConfigurator
                            .createNotification(
                                context,
                                ExecutionCodes.AFTER_EXECUTION,
                                description,
                                (duration / 60000).toInt()
                            )
                    } else {
                        notificationConfigurator
                            .createNotification(
                                context,
                                ExecutionCodes.TIME_EXECUTION,
                                description,
                                0
                            )
                    }
                }
            }
        }

        timer.schedule(task, mills)
        return timer
    }
}