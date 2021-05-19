package com.hands.clean.function.notification.factory.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.RemoteInput
import com.hands.clean.R
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.receiver.DeviceRegisterReceiver
import com.hands.clean.function.room.entry.*

class NewDeviceDetectionNotificationBuilder(
    private val context: Context,
    private val trackerEntry: TrackerEntry,
    private val notificationId: Int = NotificationIdCounter.getNotificationId()
) : BaseNotificationBuilder(context, trackerEntry.notifyInfo.channelId) {
    private val channelId = trackerEntry.notifyInfo.channelId


    init {
        this
            .setContentTitle("새로운 장치가 연결되었습니다.")
            .setContentText(getContentText())
            .addAction(getRegisterAction())
    }

    private fun getContentText(): String {
        return when (trackerEntry) {
            is BluetoothEntry -> "새로운 $channelId 기기 ${trackerEntry.deviceName} 에 연결되었습니다."
            is WifiEntry -> "새로운 $channelId 기기 ${trackerEntry.deviceName} 에 연결되었습니다."
            else -> throw Exception()
        }
    }

    private fun getRegisterAction(): NotificationCompat.Action {
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
            intent.putExtra("address", (trackerEntry as DeviceEntry).address)
            intent.putExtra("type", channelId)
            intent.putExtra("notificationId", notificationId)
        }
    }
}