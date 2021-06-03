package com.hands.clean.function.notification.factory.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.gps.LocationInfo
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.receiver.WashReceiver
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*
import java.text.SimpleDateFormat
import java.util.*

class WashNotificationSummaryBuilder(
    context: Context,
) : BaseNotificationBuilder(context, NotifyType.NotifySummary.channelId) {

    init {
        this
            .setContentTitle("손을 씻어주세요.")
            .setContentText("손 씻기 알림입니다.")
            .setGroup(context.getString(R.string.wash_notify_group_key))
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setGroupSummary(true)
            .setOnlyAlertOnce(true)
    }
}