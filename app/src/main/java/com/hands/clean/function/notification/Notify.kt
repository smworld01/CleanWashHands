package com.hands.clean.function.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hands.clean.R

abstract class Notify(private val context: Context) {
    abstract val channelId: String
    abstract val channelName: String
    open val channelDescription: String = context.getString(R.string.channel_description)

    open val contentTitle: String = context.getString(R.string.notification_title)

    lateinit var builder: NotificationCompat.Builder

    fun initChannel(): Notify {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel: NotificationChannel = createChannel()
            registerChannelInSystem(channel)
        }
        return this
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createChannel(): NotificationChannel {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        return NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun registerChannelInSystem(channel: NotificationChannel) {
        val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun setNotification(contentText: String): Notify {
        builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setDefaults(Notification.FLAG_NO_CLEAR)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return this
    }

    fun sendNotification(id: Int = 0) {

        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(id, builder.build())
        }
    }
}