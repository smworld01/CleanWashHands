package com.hands.clean.function.notification.notify

import android.app.Notification
import android.content.Context
import com.hands.clean.function.gps.LocationInfo
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.WashEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class RecordNotify(
    context: Context,
    notification: Notification,
    private val washEntry: WashEntry,
    notificationId: Int = NotificationIdCounter.getNotificationId(),
): BasicNotify(context, notification, notificationId) {
    private val notifyLimiter = NotifyLimiter(context)

    override fun onNotify() {
        thread {
            if (notifyLimiter.isNotLimited()) {
                super.onNotify()
                record()
            }
        }
    }

    private fun record() {
        DB.getInstance().washDao().insertAll(washEntry)
    }
}