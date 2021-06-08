package com.hands.clean.function.notification.factory.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.receiver.ServiceStopReceiver

class GpsPauseNotificationBuilder(
    private val context: Context,
) : BaseNotificationBuilder(context, NotifyType.NoticeService.channelId) {

    init {
        this
            .setContentTitle("서비스 일시정지됨")
            .setContentText("GPS가 비활성화 되어 서비스가 일시정지 되었습니다.")
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