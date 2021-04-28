package com.hands.clean.function.notification.type

object NotifyType {
    private val notifyTypeList = arrayListOf<NotifyInfo>(Bluetooth, GPS, Wifi)

    object Bluetooth: NotifyInfo {
        override val channelId: String = "Bluetooth"
        override val channelName: String = "블루투스 알림"
        override val channelDescription: String = "블루투스 알림입니다."
    }

    object GPS: NotifyInfo {
        override val channelId: String = "GPS"
        override val channelName: String = "GPS 알림"
        override val channelDescription: String = "GPS 알림입니다."
    }

    object Wifi : NotifyInfo {
        override val channelId: String = "WiFi"
        override val channelName: String = "와이파이 알림"
        override val channelDescription: String = "와이파이 알림입니다."
    }

    fun iterator(): MutableIterator<NotifyInfo> {
        return notifyTypeList.iterator()
    }
}


