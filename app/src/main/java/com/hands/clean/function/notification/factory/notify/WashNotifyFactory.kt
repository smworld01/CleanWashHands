package com.hands.clean.function.notification.factory.notify

import android.content.Context
import com.hands.clean.function.convertDeviceEntryToNotifyInfo
import com.hands.clean.function.notification.NotificationIdCounter
import com.hands.clean.function.notification.factory.notification.WashNotificationFactory
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.notify.RecordNotify
import com.hands.clean.function.room.entry.*

open class WashNotifyFactory(
    private val context: Context,
    private val locationInfo: LocationEntry
): NotifyFactory {
    private val notificationId = NotificationIdCounter.getNotificationId()
    val notifyInfo = convertDeviceEntryToNotifyInfo(locationInfo)

    override fun onBuild(): Notify {
        val notificationFactory = WashNotificationFactory(context, locationInfo, notificationId)
        val notification = notificationFactory.onBuild()

        val washEntry = notificationFactory.onBuildWashEntry()

        return RecordNotify(context, notification, washEntry, notificationId)
    }
}