package com.hands.clean.function.notification.type

object NotifyType {
    private val notifyTypeList = arrayListOf<NotifyInfo>(Bluetooth, GPS, Wifi, NoticeService)

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

    object NoticeService: NotifyInfo {
        override val channelId: String = "NoticeService"
        override val channelName: String = "알림 상태"
        override val channelDescription: String = "GPS, WiFi 알림을 위한 서비스입니다."

    }

    fun iterator(): MutableIterator<NotifyInfo> {
        return notifyTypeList.iterator()
    }
}


