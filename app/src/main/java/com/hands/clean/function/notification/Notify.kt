package com.hands.clean.function.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hands.clean.R
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.notification.type.NotifyType

abstract class Notify(private val context: Context, private val notifyInfo: NotifyInfo) {

    val channelId: String = notifyInfo.channelId
    private val channelName: String = notifyInfo.channelName
    private val channelDescription: String = notifyInfo.channelDescription

    abstract val contentTitle: String
    abstract val contentText: String

    lateinit var builder: NotificationCompat.Builder

    open fun setNotification(): Notify {
        builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setContentTitle(contentTitle)
            .setContentText(contentText)
            .setDefaults(Notification.FLAG_NO_CLEAR)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return this
    }

    protected fun sendNotification() {
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(NotificationIdCounter.getNotificationId(), builder.build())
        }
    }

    abstract fun send()
}