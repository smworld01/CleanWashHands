package com.hands.clean.function.room

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.hands.clean.function.notification.type.NotifyInfo
import com.hands.clean.function.notification.type.NotifyType
import com.hands.clean.function.room.entrys.*
import java.lang.Exception

@Database(entities = [WifiEntry::class, BluetoothEntry::class, WashEntry::class], version = 1)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun wifiDao(): WifiDao
    abstract fun bluetoothDao(): BluetoothDao
    abstract fun washDao(): WashDao

    fun matchDaoByEntry(deviceEntry: DeviceEntry): DeviceDao {
        return when (deviceEntry) {
            is WifiEntry -> wifiDao()
            is BluetoothEntry -> bluetoothDao()
            else -> throw Exception()
        }
    }
    fun matchDaoByNotifyType(type: NotifyInfo): DeviceDao {
        return when (type) {
            NotifyType.Wifi -> wifiDao()
            NotifyType.Bluetooth -> bluetoothDao()
            else -> throw Exception()
        }
    }
    fun matchDaoByChannelId(type: String): DeviceDao {
        return when (type) {
            NotifyType.Wifi.channelId -> wifiDao()
            NotifyType.Bluetooth.channelId -> bluetoothDao()
            else -> throw Exception()
        }
    }
}