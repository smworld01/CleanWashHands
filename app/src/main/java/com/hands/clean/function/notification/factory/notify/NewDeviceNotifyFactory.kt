package com.hands.clean.function.notification.factory.notify

import android.content.Context
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.factory.notification.NewDeviceNotificationFactory
import com.hands.clean.function.notification.notify.BasicNotify
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.room.entrys.*

class NewDeviceNotifyFactory(
    private val context: Context,
    private val locationInfo: DeviceEntry
) : NotifyFactory {
    private val notificationId = NotificationIdCounter.getNotificationId()

    override fun onBuild(): Notify {
        val notification = NewDeviceNotificationFactory(context, locationInfo).onBuild()

        return BasicNotify(context, notification, notificationId)
    }
}