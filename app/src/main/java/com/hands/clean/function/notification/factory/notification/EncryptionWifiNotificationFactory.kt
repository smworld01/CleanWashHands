package com.hands.clean.function.notification.factory.notification

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.hands.clean.R
import com.hands.clean.function.gps.LocationInfo
import com.hands.clean.function.room.entry.*
import java.text.SimpleDateFormat
import java.util.*

class EncryptionWifiNotificationFactory(
    private val context: Context,
    locationInfo: LocationEntry
) : EntryNotificationFactory(locationInfo) {

    override fun onBuild(): Notification {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(context, notifyInfo.channelId)

        builder
            .setSmallIcon(R.drawable.ic_baseline_home_24)
            .setContentTitle("손을 씻어주세요.")
            .setContentText("암호가 있는 와이파이에 연결되었습니다.")
            .setColor(ContextCompat.getColor(context, R.color.blue_500))
            .setDefaults(Notification.FLAG_NO_CLEAR)
            .priority = NotificationCompat.PRIORITY_DEFAULT

        return builder.build()
    }
    fun onBuildWashEntry(): WashEntry {
        val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)

        return WashEntry(
            date = mFormat.format(Date()),
            type = notifyInfo.channelId,
            detail = "암호가 있는 와이파이에 연결되었습니다.",
            longitude = LocationInfo.latitude,
            latitude = LocationInfo.longitude
        )
    }
}