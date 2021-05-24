package com.hands.clean.function.gps

import android.app.AlertDialog
import android.content.Intent
import android.provider.Settings
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

class SystemSettingsGpsManager(
    private val activity: AppCompatActivity,
) {
    private val checker = SystemSettingsGpsChecker(activity)
    private val startForResult = activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
        if (isEnabled()) {
            enableCallBack()
        } else {
            disableCallBack()
        }
    }

    private var enableCallBack: () -> Unit = {}
    private var disableCallBack: () -> Unit = {}

    fun registerEnableCallBack(callback: () -> Unit) {
        enableCallBack = callback
    }

    fun registerDisableCallBack(callback: () -> Unit) {
        disableCallBack = callback
    }

    fun onRequest() {
        when {
            isEnabled() -> enableCallBack()
            isDisabled() -> request()
        }
    }

    private fun request() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            """
            앱을 사용하기 위해서는 위치 서비스가 필요합니다.
            위치 설정을 수정하실래요?
            """.trimIndent()
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정") { _, _ ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startForResult.launch(callGPSSettingIntent)
        }
        builder.setNegativeButton("취소") { dialog, _ ->
            dialog.cancel()
        }
        builder.create().show()

    }

    fun isEnabled(): Boolean {
        return checker.isEnabled()
    }

    fun isDisabled(): Boolean {
        return !isEnabled()
    }
}