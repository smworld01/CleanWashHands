package com.hands.clean.function.notification.factory.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.room.entrys.*

class EncryptionWifiNotificationFactory(
    private val context: Context,
    locationInfo: LocationEntry
) : EntryNotificationFactory(locationInfo) {

    override fun onBuild(): Notification {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, notifyInfo.channelId)

        builder
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setContentTitle("손을 씻어주세요.")
            .setContentText("암호가 있는 와이파이에 연결되었습니다.")
            .setDefaults(Notification.FLAG_NO_CLEAR)
            .priority = NotificationCompat.PRIORITY_DEFAULT

        return builder.build()
    }
}