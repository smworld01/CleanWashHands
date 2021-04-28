package com.hands.clean.function.room.entrys

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WifiDao {
    @Query("SELECT * FROM WifiEntry")
    fun getAll(): List<WifiEntry>

    @Query("SELECT * FROM WifiEntry WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<WifiEntry>

    @Query("SELECT * FROM WifiEntry WHERE address = :address")
    fun findByAddress(address: String): WifiEntry?

    @Query("SELECT * FROM WifiEntry WHERE name = :name")
    fun findByName(name: String): WifiEntry?

    @Query("UPDATE WifiEntry SET notification = :bool WHERE address = :address")
    fun changeNotificationByAddress(address: String, bool: Boolean)

    @Query("UPDATE WifiEntry SET name = :name WHERE address = :address")
    fun changeNameByAddress(address: String, name: String)

    @Insert
    fun insertAll(vararg users: WifiEntry)

    @Delete
    fun delete(user: WifiEntry)
}