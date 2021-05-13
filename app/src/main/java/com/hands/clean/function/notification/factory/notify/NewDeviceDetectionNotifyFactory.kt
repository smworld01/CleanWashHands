package com.hands.clean.function.notification.factory.notify

import android.content.Context
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.factory.notification.NewDeviceDetectionNotificationFactory
import com.hands.clean.function.notification.notify.BasicNotify
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.room.entry.*

class NewDeviceDetectionNotifyFactory(
    private val context: Context,
    private val locationInfo: DeviceEntry
) : NotifyFactory {
    private val notificationId = NotificationIdCounter.getNotificationId()

    override fun onBuild(): Notify {
        val notification = NewDeviceDetectionNotificationFactory(context, locationInfo, notificationId).onBuild()

        return BasicNotify(context, notification, notificationId)
    }
}