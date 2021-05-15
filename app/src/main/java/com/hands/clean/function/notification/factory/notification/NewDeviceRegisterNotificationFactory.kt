package com.hands.clean.function.notification.factory.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.hands.clean.R

class NewDeviceRegisterNotificationFactory(
    private val context: Context,
    private val deviceName: String,
    private val type: String
) : NotificationFactory {

    override fun onBuild(): Notification {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, type)
                .setSmallIcon(R.drawable.ic_baseline_home_24)
                .setContentTitle("새로운 기기가 등록되었습니다.")
                .setContentText(getContentText())
                .setColor(ContextCompat.getColor(context, R.color.blue_500))
                .setDefaults(Notification.FLAG_NO_CLEAR)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return builder.build()
    }

    private fun getContentText(): String {
        return "새로운 $type 기기 ${deviceName}가 등록되었습니다."
    }
}