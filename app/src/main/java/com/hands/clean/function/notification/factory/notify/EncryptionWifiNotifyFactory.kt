package com.hands.clean.function.notification.factory.notify

import android.content.Context
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.factory.notification.EncryptionWifiNotificationBuilder
import com.hands.clean.function.notification.notify.BasicNotify
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.notify.RecordNotify
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*
import java.text.SimpleDateFormat
import java.util.*

class EncryptionWifiNotifyFactory(
    private val context: Context,
    private val wifiEntry: WifiEntry
) : LimiterNotifyFactory(context) {
    private val notificationId = NotificationIdCounter.getNotificationId()

    override fun onBuild(): Notify {
        val notificationFactory = EncryptionWifiNotificationBuilder(context, wifiEntry, notificationId)
        val notification = notificationFactory.build()

        val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        val date: String = mFormat.format(Date())
        wifiEntry.lastNotifyTime = date

        DB.getInstance().wifiDao().updateAll(wifiEntry)

        return BasicNotify(context, notification, notificationId)
    }
}