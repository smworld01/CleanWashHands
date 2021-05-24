package com.hands.clean.function.permission

import android.Manifest
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import com.hands.clean.function.permission.guide.BackgroundGpsPermissionUserGuide
import com.hands.clean.function.permission.guide.GpsPermissionUserGuide

class GpsPermissionRequesterWithBackground(activity: AppCompatActivity): PermissionManager(
    activity,
    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
    GpsPermissionUserGuide(activity)
) {
    private lateinit var backgroundRequester: PermissionManager
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            backgroundRequester = object: PermissionManager(
                activity,
                arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION),
                BackgroundGpsPermissionUserGuide(activity)
            ) {
                override fun isDenied(): Boolean {
                    return false
                }
            }

            super.registerGranted { backgroundRequester.onRequest() }
        }
    }

    override fun registerGranted(callback: () -> Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            backgroundRequester.registerGranted(callback)
        } else {
            super.registerGranted(callback)
        }
    }

    override fun registerDenied(callback: () -> Unit) {
        super.registerDenied(callback)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            backgroundRequester.registerDenied(callback)
        }
    }
}