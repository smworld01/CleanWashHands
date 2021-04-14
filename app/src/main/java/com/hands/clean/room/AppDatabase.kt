 package com.hands.clean.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [WifiEntry::class, BluetoothEntry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wifiDao(): WifiDao
    abstract fun bluetoothDao(): BluetoothDao
}