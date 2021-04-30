package com.hands.clean.function.room.entrys

interface DeviceEntry {
    val uid: Int
    val name: String
    val address: String
    val isNotification: Boolean
}