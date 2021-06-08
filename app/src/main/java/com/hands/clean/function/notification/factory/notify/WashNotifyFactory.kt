package com.hands.clean.function.notification.factory.notify

import android.app.Notification
import android.content.Context
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.factory.notification.WashNotificationBuilder
import com.hands.clean.function.notification.notify.BasicNotify
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.notify.WashSummaryNotify
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*
import java.text.SimpleDateFormat
import java.util.*

open class WashNotifyFactory(
    private val context: Context,
    private val trackerInfo: TrackerEntry
): LimiterNotifyFactory(context) {
    private val notificationId = NotificationIdCounter.getNotificationId()

    override fun onBuild(): Notify {
        val notification = createNotification()

        val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        val date: String = mFormat.format(Date())
        trackerInfo.lastNotifyTime = date

        when (trackerInfo) {
            is WifiEntry -> DB.getInstance().wifiDao().updateAll(trackerInfo)
            is BluetoothEntry -> DB.getInstance().bluetoothDao().updateAll(trackerInfo)
            is GpsEntry -> DB.getInstance().gpsDao().updateAll(trackerInfo)
        }

        return BasicNotify(context, notification, notificationId)
    }

    open fun createNotification(): Notification {
        val notificationFactory = WashNotificationBuilder(context, trackerInfo, notificationId)

        return notificationFactory.build()
    }
}