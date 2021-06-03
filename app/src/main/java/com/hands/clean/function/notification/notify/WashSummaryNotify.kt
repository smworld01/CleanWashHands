package com.hands.clean.function.notification.notify

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.factory.notification.WashNotificationSummaryBuilder
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.WashEntry
import kotlin.concurrent.thread

class WashSummaryNotify(
    private val context: Context,
    private val notification: Notification,
    private val notificationId: Int
): BasicNotify(context, notification, notificationId) {
    override fun onNotify() {
        super.onNotify()

        send()
    }
    private fun send() {
        with(NotificationManagerCompat.from(context)) {
            notify(2, WashNotificationSummaryBuilder(context).build())
        }
    }
}