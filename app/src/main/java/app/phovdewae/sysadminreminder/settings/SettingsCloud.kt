package app.phovdewae.sysadminreminder.settings

import android.content.Context
import java.io.File

class SettingsCloud {

    private val fileName = "settings.txt"
    private val fileNameDefault = "default_settings.txt"

    fun load(context: Context, toLoadDefault: Boolean): Settings? {
        return try {
            val directory = File(context.getExternalFilesDir(null), "settings")
            if (!directory.exists()) directory.mkdirs()

            val file: File = if (!toLoadDefault) File(directory, fileName)
            else File(directory, fileNameDefault)

            stringToSettings(file.readText())
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun stringToSettings(rawString: String): Settings {
        val pattern = Regex("[^|]+")
        val matchResult = pattern.findAll(rawString)
        var i = 0
        val settings = Settings()

        for (match in matchResult) {
            when (i) {
                0 -> settings.databaseConnectionEnabled = match.value.toBoolean()
                1 -> settings.databaseAddress = match.value
                2 -> settings.databasePort = match.value
                3 -> settings.databaseName = match.value
                4 -> settings.databaseUsername = match.value
                5 -> settings.databasePassword = match.value.toCharArray()
                6 -> settings.databaseSyncTime = match.value.toInt()
                7 -> settings.timerYellowEnabled = match.value.toBoolean()
                8 -> settings.timerYellowValue = match.value.toInt()
                9 -> settings.timerOrangeEnabled = match.value.toBoolean()
                10 -> settings.timerOrangeValue = match.value.toInt()
                11 -> settings.timerRedEnabled = match.value.toBoolean()
                12 -> settings.timerRedValue = match.value.toInt()
                13 -> settings.timerGrayEnabled = match.value.toBoolean()
                14 -> settings.timerGrayValue = match.value.toInt()
            }
            i++
        }

        return settings
    }
}