package com.hands.clean.function.notification.factory

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.notify.RecordNotify
import com.hands.clean.function.room.entrys.*
import java.lang.Exception

open class WashNotification(override val context: Context, private val locationInfo: DeviceEntry) : EntryNotificationFactory(locationInfo) {

    override fun setIcon(builder: NotificationCompat.Builder) {
        builder.setSmallIcon(R.drawable.ic_baseline_home_24)
    }

    override fun setContentText(builder: NotificationCompat.Builder) {
        val contentText: String = when (locationInfo) {
            is BluetoothEntry -> "${notifyInfo.channelId} 기기 ${locationInfo.name} 에 연결되었습니다."
            is WifiEntry -> "${notifyInfo.channelId} 기기 ${locationInfo.name} 에 연결되었습니다."
            else -> throw Exception()
        }
        builder
            .setContentTitle("손을 씻어주세요.")
            .setContentText(contentText)
    }

    override fun setOther(builder: NotificationCompat.Builder) {
        builder
            .setDefaults(Notification.FLAG_NO_CLEAR)
            .priority = NotificationCompat.PRIORITY_DEFAULT
    }

    override fun setButton(builder: NotificationCompat.Builder) {}

    override fun setNotify(notification: Notification): Notify {
        return RecordNotify(context, notification, notifyInfo)
    }
}