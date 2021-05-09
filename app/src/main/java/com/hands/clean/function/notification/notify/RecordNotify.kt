package com.hands.clean.function.notification.notify

import android.app.Notification
import android.content.Context
import com.hands.clean.function.gps.GpsTracker
import com.hands.clean.function.gps.LocationGetter
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.WashEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class RecordNotify(
    context: Context,
    notification: Notification,
    private val notifyInfo: NotifyInfo,
    notificationId: Int = NotificationIdCounter.getNotificationId()
): BasicNotify(context, notification, notificationId) {

    private val locationGetter: LocationGetter = GpsTracker(context)
    private val notifyLimiter = NotifyLimiter(context)

    override fun onNotify() {
        thread {
            if (notifyLimiter.isLimit()) {
                super.onNotify()
                record()
            }
        }
    }

    private fun record() {
        val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        var latitude: Double? = null
        var longitude: Double? = null

        latitude = locationGetter.getLatitude()
        longitude = locationGetter.getLongitude()

        val we = WashEntry(
            date = mFormat.format(Date()),
            type = notifyInfo.channelId,
            detail = "",
            longitude = longitude,
            latitude = latitude
        )
        DB.getInstance().washDao().insertAll(we)
    }
}