package com.hands.clean.function.notification.notify

import android.content.Context
import android.location.Location
import android.util.Log
import com.hands.clean.function.compareStringTimeByAbsoluteMinute
import com.hands.clean.function.gps.SystemSettingsGpsChecker
import com.hands.clean.function.permission.checker.GpsPermissionChecker
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.LocationEntry
import com.hands.clean.function.room.entry.WashEntry
import java.text.SimpleDateFormat
import java.util.*

class NotifyLimiter(context: Context) {
    private val gpsChecker: SystemSettingsGpsChecker = SystemSettingsGpsChecker(context)
    private val gpsPermissionChecker = GpsPermissionChecker(context)

    private var lastLocationRecord: LocationEntry? = null
    private var lastWashRecord: WashEntry? = null

    fun isLimited(): Boolean {
        loadLastRecord()

        return when {
            washRecordIsNotExist() -> false
            lastLocationRecordIsNotExist() -> false
            else -> limit()
        }
    }
    fun isNotLimited(): Boolean {
        return !isLimited()
    }

    private fun loadLastRecord() {
        lastLocationRecord = DB.getInstance().locationDao().getLast()
        lastWashRecord = DB.getInstance().washDao().getLatest()
    }

    private fun washRecordIsNotExist(): Boolean {
        return lastWashRecord == null
    }

    private fun lastLocationRecordIsNotExist(): Boolean {
        return lastLocationRecord == null
    }

    private fun limit(): Boolean {
        if (isRecentlySendNotify()) return true

        return when {
            isNotGetGpsInfo() -> false
            isSameLocation() -> {
                deleteAllLocationEntryExceptLast()
                true
            }
            else -> {
                deleteAllLocationEntryExceptLast()
                false
            }
        }
    }

    private fun isRecentlySendNotify(): Boolean {
        val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        val now: String = mFormat.format(Date())

        val result = compareStringTimeByAbsoluteMinute(now, lastWashRecord!!.date)
        Log.e("notify", "time diff $result Minute")
        return result < 3
    }

    private fun isNotGetGpsInfo(): Boolean {
        return !(gpsChecker.isEnabled() && gpsPermissionChecker.isGranted())
    }

    private fun isSameLocation(): Boolean {
        val locations = DB.getInstance().locationDao().getAll()

        val metersDistance = FloatArray(1)

        return locations.all {
            Location.distanceBetween(
                it.latitude, // startLatitude
                it.longitude, // startLongitude
                lastLocationRecord!!.latitude, // endLatitude
                lastLocationRecord!!.longitude, // endLongitude
                metersDistance // results
            )
            metersDistance[0] < 100
        }
    }

    private fun deleteAllLocationEntryExceptLast() {
        val entry = DB.getInstance().locationDao().getLast()
        DB.getInstance().locationDao().deleteAll()
        entry?.let { DB.getInstance().locationDao().insertAll(it) }
    }
}