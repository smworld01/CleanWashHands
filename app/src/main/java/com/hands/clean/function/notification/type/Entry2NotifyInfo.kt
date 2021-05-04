package com.hands.clean.function.notification.type

import com.hands.clean.function.room.entrys.BluetoothEntry
import com.hands.clean.function.room.entrys.DeviceEntry
import com.hands.clean.function.room.entrys.WifiEntry
import java.lang.Exception

fun convertDeviceEntryToNotifyInfo(locationInfoEntry: DeviceEntry): NotifyInfo {
    return when (locationInfoEntry) {
        is BluetoothEntry -> NotifyType.Bluetooth
        is WifiEntry -> NotifyType.Wifi
        else -> throw Exception()
    }
}