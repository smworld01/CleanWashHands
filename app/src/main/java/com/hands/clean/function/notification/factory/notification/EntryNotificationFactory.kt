package com.hands.clean.function.notification.factory.notification

import com.hands.clean.function.convertDeviceEntryToNotifyInfo
import com.hands.clean.function.room.entry.LocationEntry

abstract class EntryNotificationFactory(locationInfo: LocationEntry) : NotificationFactory {
    val notifyInfo = convertDeviceEntryToNotifyInfo(locationInfo)
}