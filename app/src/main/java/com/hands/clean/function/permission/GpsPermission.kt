package com.hands.clean.function.permission

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class GpsPermission(
        private val activity: AppCompatActivity
): Permission {
    private val requestPermissionLauncher = activity.registerForActivityResult(ActivityResultContracts.RequestPermission()) { isSuccess ->
        if(isSuccess) {
            grantedCallBack()
        } else {
            deniedCallBack()
            Toast.makeText(
                activity,
                "퍼미션이 거부되었습니다. 해당 기능을 사용하려면 허용해야 합니다.",
                Toast.LENGTH_LONG
            ).show()
            activity.finish()
        }
    }

    private var grantedCallBack: () -> Unit = {}
    private var deniedCallBack: () -> Unit = {}

    fun registerGrantedCallBack(callback: () -> Unit) {
        grantedCallBack = callback
    }

    fun registerDeniedCallBack(callback: () -> Unit) {
        deniedCallBack = callback
    }

    override fun request() {
        requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
    }

    override fun isGranted(): Boolean {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
            hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    override fun isDenied(): Boolean {
        return !isGranted()
    }
}