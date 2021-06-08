package com.hands.clean.function.notification.factory.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.receiver.ServiceStopReceiver

class ForegroundNotificationBuilder(
    private val context: Context,
) : BaseNotificationBuilder(context, NotifyType.NoticeService.channelId) {

    init {
        this
            .setContentTitle("서비스 시작됨")
            .setContentText("WiFi와 GPS를 확인하고 있습니다.")
            .addAction(getAction())
            .setGroup(context.getString(R.string.wash_notify_group_key))
    }

    private fun getAction(): NotificationCompat.Action {
        val intent: Intent = getIntent()
        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        return NotificationCompat.Action.Builder(
            R.drawable.ic_baseline_home_24, "서비스 중지", pendingIntent
        )
            .build()
    }

    private fun getIntent(): Intent {
        return Intent(context, ServiceStopReceiver::class.java)
    }
}