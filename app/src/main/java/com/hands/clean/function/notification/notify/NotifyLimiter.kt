package com.hands.clean.function.notification.notify

import android.content.Context
import android.location.Location
import android.util.Log
import com.hands.clean.function.compareStringTimeByAbsoluteMinute
import com.hands.clean.function.gps.LocationInfo
import com.hands.clean.function.gps.SystemSettingsGpsChecker
import com.hands.clean.function.permission.checker.GpsPermissionChecker
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.WashEntry
import java.text.SimpleDateFormat
import java.util.*

class NotifyLimiter(context: Context) {
    private val gpsChecker: SystemSettingsGpsChecker = SystemSettingsGpsChecker(context)
    private val gpsPermissionChecker = GpsPermissionChecker(context)

    private var lastRecord: WashEntry? = null

    fun isLimit(): Boolean {
        loadLastRecord()

        return if (isNotExistsLastRecord()) {
            true
        } else {
            limit()
        }
    }

    private fun loadLastRecord() {
        lastRecord = DB.getInstance().washDao().getLatest()
    }

    private fun isNotExistsLastRecord(): Boolean {
        return lastRecord == null
    }

    private fun limit(): Boolean {
//        if (isRecentlySendNotify()) return false

        return when {
            isNotGetGpsInfo() -> true
            isNotSameLocation() -> true
            else -> false
        }
    }

    private fun isRecentlySendNotify(): Boolean {
        val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        val now: String = mFormat.format(Date())

        val result = compareStringTimeByAbsoluteMinute(now, lastRecord!!.date)
        Log.e("notify", "time diff $result Minute")
        return result < 10
    }

    private fun isNotGetGpsInfo(): Boolean {
        if (gpsChecker.isEnabled() && gpsPermissionChecker.isGranted() && LocationInfo.isEnable()) {
            return lastRecord?.latitude == null && lastRecord?.longitude == null
        }
        return true
    }

    private fun isNotSameLocation(): Boolean {
        val metersDistance = FloatArray(1)
        Location.distanceBetween(
            lastRecord!!.latitude!!, // startLatitude
            lastRecord!!.longitude!!, // startLongitude
            LocationInfo.longitude!!, // endLatitude
            LocationInfo.latitude!!, // endLongitude
            metersDistance // results
        )

        Log.e("notify", "${LocationInfo.latitude} ${LocationInfo.longitude} / ${lastRecord!!.latitude} ${lastRecord!!.longitude}")

        Log.e("notify", "location diff ${metersDistance[0]}")
        return metersDistance[0] > 100
    }
}