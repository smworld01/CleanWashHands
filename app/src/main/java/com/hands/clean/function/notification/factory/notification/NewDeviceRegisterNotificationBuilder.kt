package com.hands.clean.function.notification.factory.notification

import android.content.Context
import com.hands.clean.function.room.entry.DeviceEntry

class NewDeviceRegisterNotificationBuilder(
    private val context: Context,
    private val deviceEntry: DeviceEntry
) : BaseNotificationBuilder(context, deviceEntry.notifyInfo.channelId) {

    init {
        this
            .setContentTitle("새로운 기기가 등록되었습니다.")
            .setContentText(getContentText())
    }

    private fun getContentText(): String {
        return "새로운 ${deviceEntry.notifyInfo.channelId} 기기 ${deviceEntry.name}이(가) 등록되었습니다."
    }
}