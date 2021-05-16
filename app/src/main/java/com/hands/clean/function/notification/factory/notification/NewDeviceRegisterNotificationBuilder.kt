package com.hands.clean.function.notification.factory.notification

import android.content.Context

class NewDeviceRegisterNotificationBuilder(
    private val context: Context,
    private val deviceName: String,
    private val type: String
) : BaseNotificationBuilder(context, type) {

    init {
        this
            .setContentTitle("새로운 기기가 등록되었습니다.")
            .setContentText(getContentText())
    }

    private fun getContentText(): String {
        return "새로운 $type 기기 ${deviceName}가 등록되었습니다."
    }
}