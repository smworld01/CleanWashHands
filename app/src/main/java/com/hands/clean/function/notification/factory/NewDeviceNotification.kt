package com.hands.clean.function.notification.factory

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.notification.notify.BasicNotify
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.receiver.ACTION_REGISTER_NOTIFICATION_DEVICE
import com.hands.clean.function.room.entrys.*
import java.lang.Exception

class NewDeviceNotification(override val context: Context, private val locationInfo: DeviceEntry) : NotificationFactory(locationInfo) {

    override fun setIcon(builder: NotificationCompat.Builder) {
        builder.setSmallIcon(R.drawable.ic_baseline_home_24)
    }

    override fun setContentText(builder: NotificationCompat.Builder) {
        val contentText: String = when (locationInfo) {
            is BluetoothEntry -> "$channelId 기기 ${locationInfo.name} 에 연결되었습니다."
            is WifiEntry -> "$channelId 기기 ${locationInfo.name} 에 연결되었습니다."
            else -> throw Exception()
        }
        builder
            .setContentTitle("손을 씻어주세요.")
            .setContentText(contentText)
    }

    override fun setOther(builder: NotificationCompat.Builder) {
        builder
            .setDefaults(Notification.FLAG_NO_CLEAR)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
    }

    override fun setButton(builder: NotificationCompat.Builder) {
        val intent: Intent = createDeviceIntent()
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        builder
            .addAction(R.drawable.ic_baseline_add_24, "등록", pendingIntent)
    }

    private fun createDeviceIntent(): Intent {
        val intent: Intent = Intent(ACTION_REGISTER_NOTIFICATION_DEVICE)
        intent.putExtra("address", locationInfo.address)
        intent.putExtra("type", channelId)
        return intent
    }

    override fun setNotify(notification: Notification): Notify {
        return BasicNotify(context, notification)
    }
}