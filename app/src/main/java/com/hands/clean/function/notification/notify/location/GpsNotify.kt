package com.hands.clean.function.notification.notify.location

import android.content.Context
import android.util.Log
import com.google.android.gms.location.Geofence
import com.hands.clean.function.notification.factory.notify.WashNotifyFactory
import com.hands.clean.function.room.DB
import com.hands.clean.function.room.entry.GpsEntry
import com.hands.clean.function.settings.WashSettingsManager
import kotlin.concurrent.thread

class GpsNotify(
    private val context: Context,
    private val geofenceList: List<Geofence>
): LocationNotify() {
    private val settings = WashSettingsManager(context)

    override fun notifySettingsIsEnable(): Boolean {
        return settings.gpsNotify
    }

    override fun doNotify() {
        if (notifySettingsIsEnable()) {
            val gpsEntry = findGpsEntryInDB()

            sendNotify(gpsEntry)
        }
    }

    private fun findGpsEntryInDB(): GpsEntry? {
        val requestIdList = geofenceList.map { geofence ->
            geofence.requestId
        }

        return try {
            DB.getInstance().gpsDao().findByRequestIds(requestIdList)[0]
        } catch (e: IndexOutOfBoundsException) {
            null
        }
    }

    private fun sendNotify(gpsEntry : GpsEntry?) {
        if (gpsEntry != null && gpsEntry.isNotification) {
            WashNotifyFactory(context, gpsEntry).onBuildWithLimiter()?.onNotify()
        }
    }
}