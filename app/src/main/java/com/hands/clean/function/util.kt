package com.hands.clean.function

import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.room.entry.BluetoothEntry
import com.hands.clean.function.room.entry.GpsEntry
import com.hands.clean.function.room.entry.TrackerEntry
import com.hands.clean.function.room.entry.WifiEntry
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


fun compareStringTimeByAbsoluteMinute(stringTime1: String, stringTime2: String): Long {
    val msTime1 = convertStringToMsTime(stringTime1)
    val msTime2 = convertStringToMsTime(stringTime2)

    return abs(msTime1 - msTime2) / (1000*60)
}


fun convertStringToMsTime(stringTime: String): Long {
    val mFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
    val date = mFormat.parse(stringTime)
    return if (date is Date) {
        date.time
    } else {
        0
    }
}


fun convertDeviceEntryToNotifyInfo(trackerInfoEntry: TrackerEntry): NotifyInfo {
    return when (trackerInfoEntry) {
        is BluetoothEntry -> NotifyType.Bluetooth
        is WifiEntry -> NotifyType.Wifi
        is GpsEntry -> NotifyType.GPS
        else -> throw Exception()
    }
}