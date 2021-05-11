package com.hands.clean.function.gps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.hands.clean.function.permission.PermissionChecker

@SuppressLint("MissingPermission")
class LocationRequester(
    private val context: Context
) {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = 30000 // todo 수치 조정
        fastestInterval = 10000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    private val locationCallback = LocationRequestCallback()
    private val gpsPermissionChecker = PermissionChecker(
        context,
        arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
    )

    init {
        val builder = LocationSettingsRequest.Builder()
                .addLocationRequest(locationRequest)

        val client: SettingsClient = LocationServices.getSettingsClient(context)
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())


        task.addOnSuccessListener { locationSettingsResponse ->
            Log.e("location settings", "success")
        }

        task.addOnFailureListener { exception ->
            Log.e("location settings", "fall")
        }
    }

    fun onRequest() {
        if (gpsPermissionChecker.isGranted()) {
            fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper())
        }
    }

    fun onPause() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }

    fun registerResultCallback(resultCallback: (LocationResult) -> Unit) {
        locationCallback.registerResultCallback(resultCallback)
    }


    private class LocationRequestCallback(): LocationCallback() {
        var resultCallback: (LocationResult) -> Unit = {}

        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            resultCallback(locationResult)
        }

        fun registerResultCallback(resultCallback: (LocationResult) -> Unit) {
            this.resultCallback = resultCallback
        }
    }
}
