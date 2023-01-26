package com.example.theperiodpurse.data

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.theperiodpurse.ui.setting.NotificationsScreen

class NotificationService(
    private val context: Context
) {
    private val notificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun showNotification() {
        val activityIntent = Intent(context, NotificationsScreen::class.java)
        val activityPendingIntent = PendingIntent.getActivity(
            context,
            1,
            activityIntent,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_IMMUTABLE else 0
        )

        val notification = NotificationCompat.Builder(context, "notification_channel")
//            .setSmallIcon()
            .setContentTitle("Test notification")
            .setContentText("The is a test")
            .setContentIntent(activityPendingIntent)
            .build()

        notificationManager.notify(1, notification)
    }
}