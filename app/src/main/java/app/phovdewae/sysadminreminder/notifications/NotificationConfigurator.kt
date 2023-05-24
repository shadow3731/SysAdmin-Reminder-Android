package app.phovdewae.sysadminreminder.notifications

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import phovdewae.sysadminreminder.R

class NotificationConfigurator {

    private val channelId = "general"

    fun createNotificationChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "General"
            val descriptionText = "The channel to instantly notify about tasks"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager = context
                .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(
        context: Context,
        executionCode: Int,
        description: String,
        minutes: Int
    ) {
        val intent = Intent(context, AlertDialog::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent
            .getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val minutesDefinition =
            if (minutes == -1 || minutes == 1)
                context.getString(R.string.notification_singular_definition)
            else context.getString(R.string.notification_plural_definition)
        val notificationTitle = when (executionCode) {
            ExecutionCodes.BEFORE_EXECUTION ->
                context.getString(R.string.notification_before_coming)
            ExecutionCodes.TIME_EXECUTION -> context.getString(R.string.notification_time_coming)
            ExecutionCodes.AFTER_EXECUTION ->
                context.getString(R.string.notification_after_coming)
            else -> context.getString(R.string.notification_before_coming)
        }
        val notificationText = when (executionCode) {
            ExecutionCodes.BEFORE_EXECUTION -> "$minutes $minutesDefinition left: $description"
            ExecutionCodes.TIME_EXECUTION -> "Now: $description"
            ExecutionCodes.AFTER_EXECUTION -> "${-(minutes)} $minutesDefinition passed: $description"
            else -> "Now: $description"
        }

        val notification = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_notification_24)
            .setContentTitle(notificationTitle)
            .setContentText(notificationText)
            .setStyle(NotificationCompat.BigTextStyle()
                .bigText(notificationText))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                ExecutionCodes.PERMISSION_REQUEST_CODE
            )
        } else {
            val notificationManager = NotificationManagerCompat.from(context)
            notificationManager.notify(1, notification)
        }
    }
}