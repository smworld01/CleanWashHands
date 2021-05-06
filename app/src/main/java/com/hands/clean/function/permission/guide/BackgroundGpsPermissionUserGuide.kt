package com.hands.clean.function.permission.guide

import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.R)
class BackgroundGpsPermissionUserGuide(
    private val activity: Activity,
): PermissionUserGuide {
    override fun showRequest(callback: () -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("백그라운드 GPS 권한 설정")
        builder.setMessage(
            """
                안드로이드 11부터는 앱이 언제나 GPS와 Wifi 사용하기 위해서는 따로 설정을 해줘야 합니다.
                설정에서 ${activity.packageManager.backgroundPermissionOptionLabel}를 체크해주세요.
            """.trimIndent()
        )
        builder.setPositiveButton("확인") { dialog, id ->
            dialog.cancel()
        }
        builder.setOnDismissListener{
            callback()
        }
        builder.create().show()
    }

    override fun showRequestFail(callback: () -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("백그라운드 GPS 권한 설정 실패")
        builder.setMessage(
            """
                권한 설정을 실패했습니다.
                권한이 없으면 해당 기능을 사용할 수 없습니다.
                만약 자동으로 설정 화면으로 넘어가지 않는다면 설정에서 직접 설정하셔야 합니다.
            """.trimIndent()
        )
        builder.setPositiveButton("닫기") { dialog, id ->
            dialog.cancel()
        }
        builder.setOnDismissListener{
            callback()
        }
        builder.create().show()
    }

    // 함수가 호출 일이 없다. 있으면 오류임
    override fun showExcuse(callback: () -> Unit) {
        throw Exception()
    }
}