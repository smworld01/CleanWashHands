package com.hands.clean.function.permission.guide

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS

class GpsPermissionUserGuide(
    private val activity: Activity
): PermissionUserGuide {
    override fun showRequest(callback: () -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("GPS 권한 설정")
        builder.setMessage(
            """
                해당 기능을 사용하기 위해서는 GPS 권한이 필요합니다.
            """.trimIndent()
        )
        builder.setPositiveButton("권한 요청") { dialog, id ->
            dialog.cancel()
        }
        builder.setOnDismissListener{
            callback()
        }
        builder.create().show()
    }

    override fun showRequestFail(callback: () -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("GPS 권한 설정")
        builder.setMessage(
            """
                GPS 권한을 얻지 못했습니다.
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

    override fun showExcuse(callback: () -> Unit) {
        val builder: AlertDialog.Builder = AlertDialog.Builder(activity)
        builder.setTitle("GPS 권한 거부")
        builder.setMessage(
            """
                GPS 권한 요청이 거부되었습니다.
                해당 기능을 사용하기 위해서는 GPS 권한이 필요합니다.
                설정에서 GPS 권한을 수락해주세요.
            """.trimIndent()
        )
        builder.setNegativeButton("설정") { dialog, id ->
            val intent = Intent().also { intent ->
                intent.action = ACTION_APPLICATION_DETAILS_SETTINGS
                intent.data = Uri.fromParts("package", activity.packageName, null)
            }
            activity.startActivity(intent)
        }
        builder.setPositiveButton("닫기") { dialog, id ->
            dialog.cancel()
        }
        builder.setOnDismissListener{
            callback()
        }
        builder.create().show()
    }
}