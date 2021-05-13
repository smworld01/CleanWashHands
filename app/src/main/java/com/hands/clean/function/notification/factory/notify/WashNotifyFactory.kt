package com.hands.clean.function.notification.factory.notify

import android.content.Context
import com.hands.clean.function.convertDeviceEntryToNotifyInfo
import com.hands.clean.function.notification.factory.notification.WashNotificationFactory
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.notify.RecordNotify
import com.hands.clean.function.room.entry.*

open class WashNotifyFactory(
    private val context: Context,
    private val locationInfo: LocationEntry
): NotifyFactory {
    val notifyInfo = convertDeviceEntryToNotifyInfo(locationInfo)

    override fun onBuild(): Notify {
        val notification = WashNotificationFactory(context, locationInfo).onBuild()

        return RecordNotify(context, notification, notifyInfo)
    }
}