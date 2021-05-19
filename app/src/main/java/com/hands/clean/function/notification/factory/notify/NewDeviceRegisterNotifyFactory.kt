package com.hands.clean.function.notification.factory.notify

import android.content.Context
import com.hands.clean.function.notification.factory.notification.NewDeviceRegisterNotificationBuilder
import com.hands.clean.function.notification.notify.BasicNotify
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.room.entry.DeviceEntry

class NewDeviceRegisterNotifyFactory(
    private val context: Context,
    private val deviceEntry: DeviceEntry,
    private val notificationId: Int
) : NotifyFactory {

    override fun onBuild(): Notify {
        val notification = NewDeviceRegisterNotificationBuilder(context, deviceEntry).build()

        return BasicNotify(context, notification, notificationId)
    }
}