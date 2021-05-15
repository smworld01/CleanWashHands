package com.hands.clean.function.notification.factory.notification

import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.hands.clean.R
import com.hands.clean.function.gps.LocationInfo
import com.hands.clean.function.receiver.WashReceiver
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

open class WashNotificationFactory(
    private val context: Context,
    private val locationInfo: LocationEntry,
    private val notificationId: Int,
): EntryNotificationFactory(locationInfo) {
    override fun onBuild(): Notification {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, notifyInfo.channelId)

        builder
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setContentTitle("손을 씻어주세요.")
            .setContentText(getContentText())
            .setColor(ContextCompat.getColor(context, R.color.blue_500))
            .addAction(getWashAction())
            .setDefaults(Notification.FLAG_NO_CLEAR)
            .priority = NotificationCompat.PRIORITY_DEFAULT

        return builder.build()
    }

    fun onBuildWashEntry(): WashEntry {
        val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)

        return WashEntry(
            date = mFormat.format(Date()),
            type = notifyInfo.channelId,
            detail = getContentText(),
            longitude = LocationInfo.latitude,
            latitude = LocationInfo.longitude
        )
    }

    private fun getContentText(): String {
        return when (locationInfo) {
            is BluetoothEntry -> "${notifyInfo.channelId} 기기 ${locationInfo.name} 에 연결되었습니다."
            is WifiEntry -> "${notifyInfo.channelId} 기기 ${locationInfo.name} 에 연결되었습니다."
            is GpsEntry -> "${locationInfo.name}에 도착하였습니다."
            else -> throw Exception()
        }
    }

    private fun getWashAction(): NotificationCompat.Action? {
        val intent: Intent = getIntent()
        val pendingIntent: PendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_ONE_SHOT)
        return NotificationCompat.Action.Builder(
            R.drawable.ic_baseline_home_24, "손 씻음", pendingIntent
        )
            .build()
    }
    private fun getIntent(): Intent {
        return Intent(context, WashReceiver::class.java)
            .putExtra("recordId", getWashUid())
            .putExtra("notificationId", notificationId)
    }
    private fun getWashUid(): Int {
        val recordId = DB.getInstance().washDao().getLatest()?.uid
        return if (recordId == null)  1
        else recordId + 1
    }

}