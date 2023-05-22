package app.phovdewae.sysadminreminder.settings

import android.content.Context
import app.phovdewae.sysadminreminder.util.isEmptyField
import java.io.File
import java.io.FileOutputStream

class SettingsCloud {

    private val fileName = "settings.txt"
    private val fileNameDefault = "default_settings.txt"

    fun save(context: Context, settings: Settings, toSaveDefault: Boolean = false): Boolean {
        return try {
            val directory = File(context.getExternalFilesDir(null), "settings")
            if (!directory.exists()) directory.mkdirs()

            val file = if (!toSaveDefault) File(directory, fileName)
            else File(directory, fileNameDefault)

            val fileOutput = FileOutputStream(file)
            fileOutput.write(settings.toStringForFile().toByteArray())
            fileOutput.close()

            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun load(context: Context, toLoadDefault: Boolean = false): Settings? {
        return try {
            val directory = File(context.getExternalFilesDir(null), "settings")
            if (!directory.exists()) {
                directory.mkdirs()
                val defaultSettings = Settings().getDefault()
                save(context, defaultSettings, true)
                save(context, defaultSettings, false)
            }

            val file: File = if (!toLoadDefault) File(directory, fileName)
            else File(directory, fileNameDefault)

            if (!file.exists()) save(context, Settings().getDefault(), toLoadDefault)

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
                1 -> settings.databaseAddress = match.value.isEmptyField(false)
                2 -> settings.databasePort = match.value.isEmptyField(false)
                3 -> settings.databaseName = match.value.isEmptyField(false)
                4 -> settings.databaseUsername = match.value.isEmptyField(false)
                5 -> {
                    val rawPassword = match.value.isEmptyField(false)
                    settings.databasePassword = if (rawPassword == "null") null
                    else rawPassword.toByteArray()
                }
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