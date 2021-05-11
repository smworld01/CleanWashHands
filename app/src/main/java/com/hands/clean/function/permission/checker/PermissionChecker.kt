package com.hands.clean.function.permission.checker

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

open class PermissionChecker(
    private val context: Context,
    private val permissions: Array<String>
) {
    open fun isGranted(): Boolean {
        val resultsPermission = permissions.map { permission ->
            ContextCompat.checkSelfPermission(
                context, permission
            )
        }
        return resultsPermission.all { it == PackageManager.PERMISSION_GRANTED }
    }

    open fun isNotGranted(): Boolean {
        return !isGranted()
    }
}