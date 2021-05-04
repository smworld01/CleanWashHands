package com.hands.clean.function.notification.factory

import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.type.convertDeviceEntryToNotifyInfo
import com.hands.clean.function.room.entrys.DeviceEntry

abstract class EntryNotificationFactory(locationInfo: DeviceEntry) : NotificationFactory() {
    override val notifyInfo = convertDeviceEntryToNotifyInfo(locationInfo)

    fun create(): Notify {
        return setNotify(build())
    }

    abstract fun setNotify(notification: Notification) : Notify
}