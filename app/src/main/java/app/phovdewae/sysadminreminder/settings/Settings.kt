package app.phovdewae.sysadminreminder.settings

class Settings(
    var databaseConnectionEnabled: Boolean?,
    var databaseAddress: String?,
    var databasePort: String?,
    var databaseName: String?,
    var databaseUsername: String?,
    var databasePassword: CharArray?,
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

    fun toStringForFile(): String {
        return "||${databaseConnectionEnabled.toString()}" +
                "||$databaseAddress" +
                "||$databasePort" +
                "||$databaseName" +
                "||$databaseUsername" +
                "||$databasePassword" +
                "||$databaseSyncTime" +
                "||$timerYellowEnabled" +
                "||$timerYellowValue" +
                "||$timerOrangeEnabled" +
                "||$timerOrangeValue" +
                "||$timerRedEnabled" +
                "||$timerRedValue" +
                "||$timerGrayEnabled" +
                "||$timerGrayValue"
    }
}