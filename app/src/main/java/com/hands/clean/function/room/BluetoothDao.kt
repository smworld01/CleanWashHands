package com.hands.clean.function.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface BluetoothDao {
    @Query("SELECT * FROM bluetoothEntry")
    fun getAll(): List<BluetoothEntry>

    @Query("SELECT * FROM bluetoothEntry WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<BluetoothEntry>

    @Query("SELECT * FROM bluetoothEntry WHERE address = :address")
    fun findByAddress(address: String): BluetoothEntry?

    @Query("SELECT * FROM bluetoothEntry WHERE name = :name")
    fun findByName(name: String): BluetoothEntry?

    @Query("UPDATE bluetoothEntry SET notification = :bool WHERE address = :address")
    fun changeNotificationByAddress(address: String, bool: Boolean)

    @Query("UPDATE bluetoothEntry SET name = :name WHERE address = :address")
    fun changeNameByAddress(address: String, name: String)

    @Insert
    fun insertAll(vararg users: BluetoothEntry)

    @Delete
    fun delete(user: BluetoothEntry)
}