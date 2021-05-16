package com.hands.clean.function.notification.factory.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.hands.clean.R

open class BaseNotificationBuilder(
    context: Context,
    channelId: String
) : NotificationCompat.Builder(context, channelId) {
    init {
        this
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setColor(ContextCompat.getColor(context, R.color.blue_500))
            .setDefaults(Notification.FLAG_NO_CLEAR)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }
}