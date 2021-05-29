package com.hands.clean.service

import android.content.Context
import com.hands.clean.function.permission.checker.GpsPermissionChecker

open class EmptyServiceManager(
    context: Context,
): MyServiceManager<EmptyService>(context, EmptyService::class.java) {
    private val gpsPermission = GpsPermissionChecker(context)

    override fun onStartService() {
        if(gpsPermission.isGranted()) {
            super.onStartService()
        }
    }
}