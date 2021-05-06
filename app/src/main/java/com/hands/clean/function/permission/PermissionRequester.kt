package com.hands.clean.function.permission

import androidx.appcompat.app.AppCompatActivity
import com.hands.clean.function.permission.guide.PermissionUserGuide

open class PermissionRequester(
    activity: AppCompatActivity,
    permissions: Array<String>,
    private val userGuide: PermissionUserGuide,
) {
    private val callback: PermissionCallback = PermissionCallback()
    private val permissionManager = PermissionManager(activity, callback, permissions)

    init {
        callback.registerRequestFailed {
            userGuide.showRequestFail { callback.callbackDenied() }
        }
    }

    fun onRequest() {
        when {
            isGranted() -> callback.callbackGranted()
            isDenied() -> userGuide.showExcuse { callback.callbackDenied() }
            else -> userGuide.showRequest { permissionManager.request() }
        }
    }


    open fun registerGranted(callback :() -> Unit) {
        this.callback.registerGranted(callback)
    }
    open fun registerDenied(callback :() -> Unit) {
        this.callback.registerDenied(callback)
    }

    open fun isGranted(): Boolean {
        return permissionManager.isGranted()
    }
    open fun isDenied(): Boolean {
        return permissionManager.isDenied()
    }
}