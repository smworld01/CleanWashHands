package com.hands.clean.function.permission

import android.content.pm.PackageManager
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat

class PermissionManager(
        private val activity: AppCompatActivity,
        private val permissionCallback: PermissionCallback,
        private val permissions: Array<String>
) {

    private val requestMultiplePermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissionMap ->
         val isSuccess = permissionMap.all {
            it.value == true
        }
        if(isSuccess) {
            permissionCallback.callbackGranted()
        } else {
            permissionCallback.callbackRequestFailed()
        }
    }

    fun request() {
        requestMultiplePermissionLauncher.launch(permissions)
    }

    fun isGranted(): Boolean {
        val resultsPermission = permissions.map { permission ->
            ContextCompat.checkSelfPermission(
                activity, permission
            )
        }
        return resultsPermission.all { it == PackageManager.PERMISSION_GRANTED }
    }

    fun isDenied(): Boolean {
        val isShowPermissions = permissions.map { permission ->
            !shouldShowRequestPermissionRationale(activity, permission)
        }

        return !isShowPermissions.all { isShow -> isShow }
    }
}