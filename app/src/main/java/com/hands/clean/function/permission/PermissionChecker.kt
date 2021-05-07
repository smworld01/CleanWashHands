package com.hands.clean.function.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat

class PermissionChecker(
    private val context: Context,
    private val permissions: Array<String>
) {
    fun isGranted(): Boolean {
        val resultsPermission = permissions.map { permission ->
            ContextCompat.checkSelfPermission(
                context, permission
            )
        }
        return resultsPermission.all { it == PackageManager.PERMISSION_GRANTED }
    }

    fun isNotGranted(): Boolean {
        return !isGranted()
    }
}