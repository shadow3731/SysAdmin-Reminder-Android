package phovdewae.sysadminreminder.timers

data class TaskTimer(
    var name: String,
    var duration: Long,
    var backgroundColor: Int,
    var isEnable: Boolean
    )