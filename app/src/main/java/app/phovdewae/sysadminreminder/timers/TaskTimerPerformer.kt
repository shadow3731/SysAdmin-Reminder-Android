package app.phovdewae.sysadminreminder.timers

import android.graphics.Color
import androidx.cardview.widget.CardView
import app.phovdewae.sysadminreminder.util.makeBackground
import java.io.Serializable
import java.util.Date
import java.util.Timer
import java.util.TimerTask

class TaskTimerPerformer: Serializable {

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

    fun addTimers(position: Int?, timestamp: Date, cardView: CardView) {
        val current = timestamp.time - Date().time

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
                        taskTimerLists[i].backgroundColor,
                        cardView
                    ))
                } else {
                    timers[position].add(createTimerTask(
                        difference,
                        taskTimerLists[i].backgroundColor,
                        cardView
                    ))
                }
            } else {
                if (position == null) timers[timers.size - 1].add(null)
                else timers[position].add(null)
            }
        }
    }

    fun editTimers(position: Int, timestamp: Date) {
        deleteTimers(position, true)
        addTimers(position, timestamp, cardViews[position])
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