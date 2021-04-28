package com.hands.clean.function.notification.init

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.hands.clean.function.notification.type.NotifyType

class NotificationChannelManager(private val context: Context) {
    fun initChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val it = NotifyType.iterator()

            while (it.hasNext()) {
                val notifyInfo = it.next()
                val channel = createChannel(notifyInfo.channelId, notifyInfo.channelName, notifyInfo.channelDescription)
                registerChannelInSystem(channel)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannel(channelId: String, channelName: String, channelDescription: String): NotificationChannel {
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        return NotificationChannel(channelId, channelName, importance).apply {
            description = channelDescription
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun registerChannelInSystem(channel: NotificationChannel) {
        val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}