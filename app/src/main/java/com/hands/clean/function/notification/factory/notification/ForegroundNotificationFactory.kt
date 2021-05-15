package com.hands.clean.function.notification.factory.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.hands.clean.R
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.receiver.ServiceStopReceiver
import com.hands.clean.function.service.WashLocationServiceManager

class ForegroundNotificationFactory(
    private val context: Context,
    private val notifyInfo: NotifyInfo
) : NotificationFactory {
    private val service = WashLocationServiceManager(context)
    override fun onBuild(): Notification {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, notifyInfo.channelId)
                .setSmallIcon(R.drawable.ic_baseline_home_24)
                .setContentTitle("WiFi와 GPS를 확인하고 있습니다.")
                .setContentText("테스트 중..")
                .addAction(getAction())
                .setColor(ContextCompat.getColor(context, R.color.blue_500))
                .setDefaults(Notification.FLAG_NO_CLEAR)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return builder.build()
    }

    private fun getAction(): NotificationCompat.Action? {
        val intent: Intent = getIntent()
        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        return NotificationCompat.Action.Builder(
            R.drawable.ic_baseline_home_24, "중지", pendingIntent
        )
            .build()
    }

    private fun getIntent(): Intent {
        return Intent(context, ServiceStopReceiver::class.java)
    }
}