package com.hands.clean.function.notification.factory

import android.content.Context
import androidx.core.app.NotificationCompat
import com.hands.clean.function.room.entrys.*

class EncryptionWifiNotification(context: Context, locationInfo: DeviceEntry) : WashNotification(context, locationInfo) {
    override fun setContentText(builder: NotificationCompat.Builder) {
        builder
            .setContentTitle("손을 씻어주세요.")
            .setContentText("암호가 있는 와이파이에 연결되었습니다.")
    }
}