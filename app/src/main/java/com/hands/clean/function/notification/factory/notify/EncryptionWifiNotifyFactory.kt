package com.hands.clean.function.notification.factory.notify

import android.content.Context
import com.hands.clean.function.convertDeviceEntryToNotifyInfo
import com.hands.clean.function.notification.factory.notification.EncryptionWifiNotificationFactory
import com.hands.clean.function.notification.notify.Notify
import com.hands.clean.function.notification.notify.RecordNotify
import com.hands.clean.function.room.entry.*

class EncryptionWifiNotifyFactory(
    private val context: Context,
    private val wifiEntry: WifiEntry
) : NotifyFactory {
    val notifyInfo = convertDeviceEntryToNotifyInfo(wifiEntry)

    override fun onBuild(): Notify {
        val notificationFactory = EncryptionWifiNotificationFactory(context, wifiEntry)
        val notification = notificationFactory.onBuild()

        val washEntry = notificationFactory.onBuildWashEntry()

        return RecordNotify(context, notification, washEntry)
    }
}