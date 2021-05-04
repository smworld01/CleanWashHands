package com.hands.clean.function.notification.notify

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.hands.clean.function.notification.NotificationIdCounter

class BasicNotify(private val context: Context, private val notification: Notification): Notify {
    override fun onNotify() {
        send()
    }

    private fun send() {
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(NotificationIdCounter.getNotificationId(), notification)
        }
    }
}