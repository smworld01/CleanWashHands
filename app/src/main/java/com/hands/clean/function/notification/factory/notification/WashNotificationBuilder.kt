package com.hands.clean.function.notification.factory.notification

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.hands.clean.R
import com.hands.clean.function.gps.LocationInfo
import com.hands.clean.receiver.WashReceiver
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.*
import java.text.SimpleDateFormat
import java.util.*

open class WashNotificationBuilder(
    private val context: Context,
    private val trackerEntry: TrackerEntry,
    private val notificationId: Int,
) : BaseNotificationBuilder(context, trackerEntry.notifyInfo.channelId) {
    private val channelId = trackerEntry.notifyInfo.channelId

    init {
        this
            .setContentTitle("손을 씻어주세요.")
            .setContentText(getContentText())
            .addAction(getWashAction())
    }

    fun onBuildWashEntry(): WashEntry {
        val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)

        return WashEntry(
            date = mFormat.format(Date()),
            type = channelId,
            detail = getContentText(),
            longitude = LocationInfo.latitude,
            latitude = LocationInfo.longitude
        )
    }

    open fun getContentText(): String {
        return when (trackerEntry) {
            is BluetoothEntry -> "$channelId 기기 ${trackerEntry.name} 에 연결되었습니다."
            is WifiEntry -> "$channelId 기기 ${trackerEntry.name} 에 연결되었습니다."
            is GpsEntry -> "${trackerEntry.name}에 도착하였습니다."
            else -> throw Exception()
        }
    }

    private fun getWashAction(): NotificationCompat.Action {
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