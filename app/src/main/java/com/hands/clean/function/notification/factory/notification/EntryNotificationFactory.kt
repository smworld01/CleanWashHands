package com.hands.clean.function.notification.factory.notification

import com.hands.clean.function.notification.type.convertDeviceEntryToNotifyInfo
import com.hands.clean.function.room.entrys.DeviceEntry

abstract class EntryNotificationFactory(locationInfo: DeviceEntry) : NotificationFactory {
    val notifyInfo = convertDeviceEntryToNotifyInfo(locationInfo)
}