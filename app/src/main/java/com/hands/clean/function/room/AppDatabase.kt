package com.hands.clean.function.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hands.clean.function.room.entrys.*

@Database(entities = [WifiEntry::class, BluetoothEntry::class, WashEntry::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wifiDao(): WifiDao
    abstract fun bluetoothDao(): BluetoothDao
    abstract fun washDao(): WashDao
}