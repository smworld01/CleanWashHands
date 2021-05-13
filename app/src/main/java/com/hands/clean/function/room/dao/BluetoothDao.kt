package com.hands.clean.function.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hands.clean.function.room.entry.BluetoothEntry
import com.hands.clean.function.room.entry.DeviceEntry

@Dao
interface BluetoothDao: DeviceDao {
    @Query("SELECT * FROM BluetoothEntry")
    fun getAllBluetooth(): List<BluetoothEntry>

    @Query("SELECT * FROM BluetoothEntry WHERE uid IN (:userIds)")
    fun loadAllByIdsBluetooth(userIds: IntArray): List<BluetoothEntry>

    @Query("SELECT * FROM BluetoothEntry WHERE address = :address")
    fun findByAddressBluetooth(address: String): BluetoothEntry?

    @Query("SELECT * FROM BluetoothEntry WHERE name = :name")
    fun findByNameBluetooth(name: String): BluetoothEntry?

    @Query("UPDATE BluetoothEntry SET notification = :bool WHERE address = :address")
    fun changeNotificationByAddressBluetooth(address: String, bool: Boolean)

    @Query("UPDATE BluetoothEntry SET name = :name WHERE address = :address")
    fun changeNameByAddressBluetooth(address: String, name: String)

    @Insert
    fun insertAllBluetooth(vararg users: BluetoothEntry)

    @Delete
    fun deleteBluetooth(user: BluetoothEntry)


    override fun getAll(): List<DeviceEntry> {
        return getAllBluetooth()
    }
    override fun loadAllByIds(userIds: IntArray): List<DeviceEntry> {
        return loadAllByIdsBluetooth(userIds)
    }
    override fun findByAddress(address: String): BluetoothEntry? {
           return findByAddressBluetooth (address)
    }
    override fun findByName(name: String): BluetoothEntry? {
        return findByNameBluetooth(name)
    }
    override fun changeNotificationByAddress(address: String, bool: Boolean) {
        changeNotificationByAddressBluetooth(address, bool)
    }
    override fun changeNameByAddress(address: String, name: String) {
        changeNameByAddressBluetooth(address, name)
    }
    override fun insertAll(vararg users: DeviceEntry) {
        insertAllBluetooth(*(users.map { it as BluetoothEntry }.toTypedArray()))
    }
    override fun delete(user: DeviceEntry) {
        deleteBluetooth(user as BluetoothEntry)
    }
}