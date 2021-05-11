package com.hands.clean.function.service

import android.content.Context
import com.hands.clean.function.permission.checker.GpsPermissionChecker
import com.hands.clean.function.permission.checker.PermissionChecker

open class WashLocationServiceManager(
    private val context: Context,
): MyServiceManager<WashLocationService>(context, WashLocationService::class.java) {
    private val gpsPermission = GpsPermissionChecker(context)

    override fun onStartService() {
        if(gpsPermission.isGranted()) {
            super.onStartService()
        }
    }
}