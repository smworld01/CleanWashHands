package com.hands.clean.function.gps

import android.content.Context
import android.location.LocationManager

class SystemSettingsGpsChecker(
    private val context: Context
) {
    fun isEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    fun isDisabled(): Boolean {
        return !isEnabled()
    }
}