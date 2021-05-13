package com.hands.clean.function.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.hands.clean.function.room.entry.DeviceEntry
import com.hands.clean.function.room.entry.WifiEntry

@Dao
interface WifiDao: DeviceDao {
    @Query("SELECT * FROM WifiEntry")
    fun getAllWifi(): List<WifiEntry>

    @Query("SELECT * FROM WifiEntry WHERE uid IN (:userIds)")
    fun loadAllByIdsWifi(userIds: IntArray): List<WifiEntry>

    @Query("SELECT * FROM WifiEntry WHERE address = :address")
    fun findByAddressWifi(address: String): WifiEntry?

    @Query("SELECT * FROM WifiEntry WHERE name = :name")
    fun findByNameWifi(name: String): WifiEntry?

    @Query("UPDATE WifiEntry SET notification = :bool WHERE address = :address")
    fun changeNotificationByAddressWifi(address: String, bool: Boolean)

    @Query("UPDATE WifiEntry SET name = :name WHERE address = :address")
    fun changeNameByAddressWifi(address: String, name: String)

    @Insert
    fun insertAllWifi(vararg users: WifiEntry)

    @Delete
    fun deleteWifi(user: WifiEntry)

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
    override fun changeNotificationByAddress(address: String, bool: Boolean) {
        changeNotificationByAddressWifi(address, bool)
    }
    override fun changeNameByAddress(address: String, name: String) {
        changeNameByAddressWifi(address, name)
    }
    override fun insertAll(vararg users: DeviceEntry) {
        insertAllWifi(*(users.map { it as WifiEntry }.toTypedArray()))
    }
    override fun delete(user: DeviceEntry) {
        deleteWifi(user as WifiEntry)
    }
}