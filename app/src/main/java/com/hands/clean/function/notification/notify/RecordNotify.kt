package com.hands.clean.function.notification.notify

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.WashEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class RecordNotify(private val context: Context, private val notification: Notification, private val notifyInfo: NotifyInfo): Notify {


    override fun onNotify() {
        record()
        send()
    }

    private fun record() {
        thread {
            val now = Date()
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            val we = WashEntry(0, format.format(now), notifyInfo.channelId, "", false)
            DB.getInstance().washDao().insertAll(we)
        }
    }

    private fun send() {
        with(NotificationManagerCompat.from(context)) {
            // notificationId is a unique int for each notification that you must define
            notify(NotificationIdCounter.getNotificationId(), notification)
        }
    }
}