package com.hands.clean.function.room.entry

import com.hands.clean.function.notification.type.NotifyInfo

interface LocationEntry {
    val uid: Int
    val name: String
    val isNotification: Boolean
    val notifyInfo: NotifyInfo
}