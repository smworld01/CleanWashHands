package com.hands.clean.function.notification.factory

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.notification.type.NotifyInfo

class ForegroundNotification(override val context: Context, override val notifyInfo: NotifyInfo) : NotificationFactory() {
    override fun setIcon(builder: NotificationCompat.Builder) {
        builder.setSmallIcon(R.drawable.ic_baseline_home_24)
    }

    override fun setContentText(builder: NotificationCompat.Builder) {
        builder
                .setContentTitle("WiFi와 GPS를 확인하고 있습니다.")
                .setContentText("테스트 중..")
    }

    override fun setOther(builder: NotificationCompat.Builder) {
        builder
                .setDefaults(Notification.FLAG_NO_CLEAR)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    override fun setButton(builder: NotificationCompat.Builder) {

    }
}