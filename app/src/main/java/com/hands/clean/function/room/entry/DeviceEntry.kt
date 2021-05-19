package com.hands.clean.function.room.entry

interface DeviceEntry: TrackerEntry {
    val deviceName: String
    val address: String
}