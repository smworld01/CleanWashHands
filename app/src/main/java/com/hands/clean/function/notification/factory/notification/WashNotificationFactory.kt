package com.hands.clean.function.notification.factory.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.room.entrys.*
import java.lang.Exception

open class WashNotificationFactory(
    private val context: Context,
    private val locationInfo: LocationEntry
): EntryNotificationFactory(locationInfo) {
    override fun onBuild(): Notification {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, notifyInfo.channelId)

        builder
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setContentTitle("손을 씻어주세요.")
            .setContentText(getContentText())
            .setDefaults(Notification.FLAG_NO_CLEAR)
            .priority = NotificationCompat.PRIORITY_DEFAULT

        return builder.build()
    }

    private fun getContentText(): String {
        return when (locationInfo) {
            is BluetoothEntry -> "${notifyInfo.channelId} 기기 ${locationInfo.name} 에 연결되었습니다."
            is WifiEntry -> "${notifyInfo.channelId} 기기 ${locationInfo.name} 에 연결되었습니다."
            is GpsEntry -> "${locationInfo.name}에 도착하였습니다."
            else -> throw Exception()
        }
    }
}