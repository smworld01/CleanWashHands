package com.hands.clean.function.notification.factory.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.notification.type.NotifyInfo

class ForegroundNotificationFactory(
    private val context: Context,
    private val notifyInfo: NotifyInfo) : NotificationFactory {

    override fun onBuild(): Notification {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, notifyInfo.channelId)
                .setSmallIcon(R.drawable.ic_baseline_home_24)
                .setContentTitle("WiFi와 GPS를 확인하고 있습니다.")
                .setContentText("테스트 중..")
                .setDefaults(Notification.FLAG_NO_CLEAR)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return builder.build()
    }
}