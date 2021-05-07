package com.hands.clean.function.notification.notify

import android.Manifest
import android.app.Notification
import android.content.Context
import android.location.Location
import com.hands.clean.function.compareStringTimeByAbsoluteMinute
import com.hands.clean.function.gps.LocationGetter
import com.hands.clean.function.gps.SystemSettingsGpsChecker
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.permission.PermissionChecker
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entrys.WashEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class RecordNotify(
    private val context: Context,
    private val notification: Notification,
    private val notifyInfo: NotifyInfo,
    notificationId: Int = NotificationIdCounter.getNotificationId()
): BasicNotify(context, notification, notificationId) {
    private val gpsChecker: SystemSettingsGpsChecker = SystemSettingsGpsChecker(context)
    private val gpsPermissionChecker: PermissionChecker = PermissionChecker(
        context,
        arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION // todo R 버전 분기
        )
    )
    private lateinit var locationGetter: LocationGetter

    private val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
    private val now: String = mFormat.format(Date())

    override fun onNotify() {
        thread {
            val latestRecord = loadLatestRecord()
            if (latestRecord == null) {
                send()
            } else {
                if (isRecentlySend(latestRecord)) {
                    if (isGetGpsInfo(latestRecord) && isNotSameLocation(latestRecord)) {
                        send()
                    }
                }
            }
        }
    }

    private fun loadLatestRecord(): WashEntry? {
        return DB.getInstance().washDao().getLatest()
    }

    private fun send() {
        super.onNotify()
        record()
    }

    private fun isRecentlySend(latestRecord: WashEntry): Boolean {
        return compareStringTimeByAbsoluteMinute(now, latestRecord.date) > 10
    }

    private fun isGetGpsInfo(latestRecord: WashEntry): Boolean {
        if (gpsChecker.isEnabled() && gpsPermissionChecker.isGranted()) {
            return latestRecord.latitude == null && latestRecord.longitude == null
        }
        return false
    }

    private fun isNotSameLocation(latestRecord: WashEntry): Boolean {
        val metersDistance = FloatArray(1)
        Location.distanceBetween(
            latestRecord.latitude!!, // startLatitude
            latestRecord.longitude!!, // startLongitude
            locationGetter.getLatitude(), // endLatitude
            locationGetter.getLongitude(), // endLongitude
            metersDistance // results
        )

        return metersDistance[0] < 100
    }

    private fun record() {
        var latitude: Double? = null
        var longitude: Double? = null

        if (gpsChecker.isEnabled() && gpsPermissionChecker.isGranted()) {
            latitude = locationGetter.getLatitude()
            longitude = locationGetter.getLongitude()
        }
        val we = WashEntry(
            date = now,
            type = notifyInfo.channelId,
            detail = "",
            longitude = longitude,
            latitude = latitude
        )
        DB.getInstance().washDao().insertAll(we)
    }
}