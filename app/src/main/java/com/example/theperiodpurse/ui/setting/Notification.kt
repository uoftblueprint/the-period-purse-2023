package com.example.theperiodpurse.ui.setting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.example.theperiodpurse.MainActivity
import com.example.theperiodpurse.R

class Notification(context: Context) {

    val channelID:String = "TPP100"
    val channelName:String = "TPPNotification"
    val notificationManager = context.applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    lateinit var notificationChannel: NotificationChannel
    lateinit var notificationBuilder: NotificationCompat.Builder

    fun FirNotification(context: Context, title: String, content: String){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationChannel = NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val intent = Intent(context, NotificationsScreen:: class.java)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        notificationBuilder = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.app_logo)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .addAction(R.drawable.app_logo, "Open", pendingIntent)
        notificationManager.notify(100, notificationBuilder.build())
    }







}
//
//private fun createNotificationChannel(CHANNEL_ID: String) {
//    // Create the NotificationChannel, but only on API 26+ because
//    // the NotificationChannel class is new and not in the support library
//    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        val name = "TPP100"
//        val descriptionText = "testing channels"
//        val importance = NotificationManager.IMPORTANCE_DEFAULT
//        val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
//            description = descriptionText
//        }
//        // Register the channel with the system
//        val notificationManager: NotificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//        notificationManager.createNotificationChannel(channel)
//    }
//}

