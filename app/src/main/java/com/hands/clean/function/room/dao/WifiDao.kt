package com.hands.clean.function.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hands.clean.function.room.entry.DeviceEntry
import com.hands.clean.function.room.entry.WifiEntry

@Dao
interface WifiDao: DeviceDao {
    @Query("SELECT * FROM WifiEntry")
    fun getAllWifi(): List<WifiEntry>

    @Query("SELECT * FROM WifiEntry")
    fun getAllByLiveData(): LiveData<List<WifiEntry>>

    @Query("SELECT * FROM WifiEntry WHERE uid IN (:userIds)")
    fun loadAllByIdsWifi(userIds: IntArray): List<WifiEntry>

    @Query("SELECT * FROM WifiEntry WHERE address = :address")
    fun findByAddressWifi(address: String): WifiEntry?

    @Query("SELECT * FROM WifiEntry WHERE name = :name")
    fun findByNameWifi(name: String): WifiEntry?

    @Insert
    fun insertAllWifi(vararg entries: WifiEntry)

    @Update
    fun updateAllWifi(vararg entries: WifiEntry)

    @Delete
    fun deleteWifi(entry: WifiEntry)

    override fun getAll(): List<DeviceEntry> {
        return getAllWifi()
    }
    override fun loadAllByIds(userIds: IntArray): List<DeviceEntry> {
        return loadAllByIdsWifi(userIds)
    }
    override fun findByAddress(address: String): WifiEntry? {
           return findByAddressWifi (address)
    }
    override fun findByName(name: String): WifiEntry? {
        return findByNameWifi(name)
    }
    override fun insertAll(vararg entries: DeviceEntry) {
        insertAllWifi(*(entries.map { it as WifiEntry }.toTypedArray()))
    }

    override fun updateAll(vararg entries: DeviceEntry) {
        updateAllWifi(*(entries.map { it as WifiEntry }.toTypedArray()))
    }
    override fun delete(entry: DeviceEntry) {
        deleteWifi(entry as WifiEntry)
    }
}