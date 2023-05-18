package phovdewae.sysadminreminder.timers

import android.graphics.Color
import androidx.cardview.widget.CardView
import phovdewae.sysadminreminder.util.makeBackground
import java.util.Date
import java.util.Timer
import java.util.TimerTask

class TaskTimerPerformer {

    private val defaultTaskTimerList = arrayListOf(
        TaskTimer(3600000, Color.YELLOW, true),
        TaskTimer(1200000, Color.rgb(225, 165, 0), true),
        TaskTimer(0, Color.RED, true),
        TaskTimer(-300000, Color.GRAY, true)
    )
    private var taskTimerLists = ArrayList(defaultTaskTimerList)
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

    fun addTimers(position: Int?, timestamp: Date, cardView: CardView) {
        val current = timestamp.time - Date().time
        timers.add(ArrayList(taskTimerLists.size))

        if (position == null) cardViews.add(cardView)

        for (i in 0 until taskTimerLists.size) {
            if (taskTimerLists[i].isEnable && current > taskTimerLists[i].duration) {
                val difference = current - taskTimerLists[i].duration
                if (position == null) {
                    timers[timers.size - 1].add(createTimerTask(
                        difference,
                        taskTimerLists[i].backgroundColor,
                        cardView
                    ))
                } else {
                    timers[timers.size - 1].add(position, createTimerTask(
                        difference,
                        taskTimerLists[i].backgroundColor,
                        cardView
                    ))
                }
            }
        }
    }

    fun editTimers(position: Int, timestamp: Date) {
        deleteTimers(position, true)
        addTimers(position, timestamp, cardViews[position])
    }

    fun deleteTimers(position: Int, forReschedule: Boolean) {
        for (i in 0 until timers[position].size) {
            timers[position][i]?.schedule(object : TimerTask() {
                override fun run() {
                    timers[position][i]?.cancel()
                }
            }, 0)
        }

        timers.removeAt(position)
        if (!forReschedule) cardViews.removeAt(position)
    }

    private fun createTimerTask(mills: Long, color: Int, cardView: CardView): Timer {
        val timer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                makeBackground(color, cardView)
            }
        }

        timer.schedule(task, mills)
        return timer
    }
}