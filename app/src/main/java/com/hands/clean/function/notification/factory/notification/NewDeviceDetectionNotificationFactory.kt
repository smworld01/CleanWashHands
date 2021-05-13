package com.hands.clean.function.notification.factory.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.hands.clean.R
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.receiver.DeviceRegisterReceiver
import com.hands.clean.function.room.entry.*
import java.lang.Exception

class NewDeviceDetectionNotificationFactory(
    private val context: Context,
    private val locationInfo: DeviceEntry,
    private val notificationId: Int = NotificationIdCounter.getNotificationId()
) : EntryNotificationFactory(locationInfo) {

    override fun onBuild(): Notification {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, notifyInfo.channelId)
                .setSmallIcon(R.drawable.ic_baseline_home_24)
                .setContentTitle("새로운 장치가 연결되었습니다.")
                .setContentText(getContentText())
                .addAction(getButton())
                .setDefaults(Notification.FLAG_NO_CLEAR)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        return builder.build()
    }

    private fun getContentText(): String {
        return when (locationInfo) {
            is BluetoothEntry -> "새로운 ${notifyInfo.channelId} 기기 ${locationInfo.name} 에 연결되었습니다."
            is WifiEntry -> "새로운 ${notifyInfo.channelId} 기기 ${locationInfo.name} 에 연결되었습니다."
            else -> throw Exception()
        }
    }

    private fun getButton(): NotificationCompat.Action {
        val intent: Intent = createDeviceIntent()
        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT);
        val remoteInput: RemoteInput = RemoteInput.Builder("deviceName")
            .setLabel("등록할 이름")
            .build()
        return NotificationCompat.Action.Builder(
            R.drawable.ic_baseline_home_24, "등록", pendingIntent
        )
            .addRemoteInput(remoteInput)
            .build()
    }

    private fun createDeviceIntent(): Intent {
        return Intent(context, DeviceRegisterReceiver::class.java).also { intent ->
            intent.putExtra("address", locationInfo.address)
            intent.putExtra("type", notifyInfo.channelId)
            intent.putExtra("notificationId", notificationId)
        }
    }
}