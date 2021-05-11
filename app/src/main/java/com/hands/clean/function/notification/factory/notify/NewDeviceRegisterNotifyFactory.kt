package com.hands.clean.function.notification.factory.notify

import android.content.Context
import com.hands.clean.function.notification.factory.notification.NewDeviceRegisterNotificationFactory
import com.hands.clean.function.notification.notify.BasicNotify
import com.hands.clean.function.notification.notify.Notify

class NewDeviceRegisterNotifyFactory(
    private val context: Context,
    private val deviceName: String,
    private val type: String,
    private val notificationId: Int
) : NotifyFactory {

    override fun onBuild(): Notify {
        val notification = NewDeviceRegisterNotificationFactory(context, deviceName, type).onBuild()

        return BasicNotify(context, notification, notificationId)
    }
}