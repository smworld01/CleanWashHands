package com.hands.clean.function.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hands.clean.function.room.entry.BluetoothEntry
import com.hands.clean.function.room.entry.DeviceEntry

@Dao
interface BluetoothDao: DeviceDao {
    @Query("SELECT * FROM BluetoothEntry")
    fun getAllBluetooth(): List<BluetoothEntry>

    @Query("SELECT * FROM BluetoothEntry")
    fun getAllByLiveData(): LiveData<List<BluetoothEntry>>

    @Query("SELECT * FROM BluetoothEntry WHERE uid IN (:userIds)")
    fun loadAllByIdsBluetooth(userIds: IntArray): List<BluetoothEntry>

    @Query("SELECT * FROM BluetoothEntry WHERE address = :address")
    fun findByAddressBluetooth(address: String): BluetoothEntry?

    @Query("SELECT * FROM BluetoothEntry WHERE name = :name")
    fun findByNameBluetooth(name: String): BluetoothEntry?

    @Insert
    fun insertAllBluetooth(vararg entries: BluetoothEntry)

    @Update
    fun updateAllBluetooth(vararg entries: BluetoothEntry)

    @Delete
    fun deleteBluetooth(entry: BluetoothEntry)


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
    override fun insertAll(vararg entries: DeviceEntry) {
        insertAllBluetooth(*(entries.map { it as BluetoothEntry }.toTypedArray()))
    }
    override fun updateAll(vararg entries: DeviceEntry) {
        updateAllBluetooth(*(entries.map { it as BluetoothEntry }.toTypedArray()))
    }
    override fun delete(entry: DeviceEntry) {
        deleteBluetooth(entry as BluetoothEntry)
    }
}