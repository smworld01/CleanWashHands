package com.hands.clean.function.permission.checker

import android.Manifest
import android.content.Context
import android.os.Build

class GpsPermissionChecker(context: Context) : PermissionChecker(
    context,
    arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )
) {
    private var backgroundPermissionChecker: PermissionChecker? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            backgroundPermissionChecker = PermissionChecker(context, arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION))
        }
    }

    override fun isGranted(): Boolean {
        return super.isGranted() && backgroundPermissionChecker != null && backgroundPermissionChecker!!.isGranted()
    }

    override fun isNotGranted(): Boolean {
        return !isGranted()
    }
}