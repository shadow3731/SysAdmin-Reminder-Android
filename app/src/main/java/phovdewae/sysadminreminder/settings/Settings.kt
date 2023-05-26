package phovdewae.sysadminreminder.settings

class Settings(
    var databaseConnectionEnabled: Boolean?,
    var databaseAddress: String?,
    var databasePort: String?,
    var databaseName: String?,
    var databaseUsername: String?,
    var databasePassword: ByteArray?,
    var databaseSyncTime: Int?,
    var timerYellowEnabled: Boolean?,
    var timerYellowValue: Int?,
    var timerOrangeEnabled: Boolean?,
    var timerOrangeValue: Int?,
    var timerRedEnabled: Boolean?,
    var timerRedValue: Int?,
    var timerGrayEnabled: Boolean?,
    var timerGrayValue: Int?
) {

    constructor() : this(
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null,
        null
    )

    fun getDefault(): Settings {
        return Settings(
            false,
            "null",
            "null",
            "null",
            "null",
            null,
            2,
            true,
            60,
            true,
            20,
            true,
            0,
            true,
            -5)
    }

    override fun toString(): String {
        return "Settings(" +
                "$databaseConnectionEnabled, " +
                "$databaseAddress, " +
                "$databasePort, " +
                "$databaseName, " +
                "$databaseUsername, " +
                "$databasePassword, " +
                "$databaseSyncTime, " +
                "$timerYellowEnabled, " +
                "$timerYellowValue, " +
                "$timerOrangeEnabled, " +
                "$timerOrangeValue, " +
                "$timerRedEnabled, " +
                "$timerRedValue, " +
                "$timerGrayEnabled, " +
                "$timerGrayValue)"
    }
}