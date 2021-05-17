package com.hands.clean.function.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hands.clean.function.room.entry.GpsEntry

@Dao
interface GpsDao {
    @Query("SELECT * FROM GpsEntry")
    fun getAll(): List<GpsEntry>

    @Query("SELECT * FROM GpsEntry")
    fun getAllByLiveData(): LiveData<List<GpsEntry>>

    @Query("SELECT * FROM GpsEntry WHERE uid IN (:unitIds)")
    fun loadAllByIds(unitIds: IntArray): List<GpsEntry>

    @Query("SELECT * FROM GpsEntry WHERE requestId = :requestId")
    fun findByRequestId(requestId: String): GpsEntry?

    @Query("SELECT * FROM GpsEntry WHERE requestId IN (:requestId)")
    fun findByRequestIds(requestId: List<String>): List<GpsEntry>

    @Query("SELECT * FROM GpsEntry WHERE name = :name")
    fun findByName(name: String): GpsEntry?

    @Query("UPDATE GpsEntry SET notification = :bool WHERE requestId = :requestId")
    fun changeNotificationByRequestId(requestId: String, bool: Boolean)

    @Query("UPDATE GpsEntry SET name = :name WHERE requestId = :requestId")
    fun changeNameByRequestId(requestId: String, name: String)

    @Query("UPDATE GpsEntry SET radius = :radius WHERE requestId = :requestId")
    fun changeRadiusByRequestId(requestId: String, radius: Float)

    @Query("DELETE FROM GpsEntry WHERE requestID = :requestId")
    fun deleteByRequestId(requestId: String)

    @Update
    fun updateAll(vararg gpsEntry: GpsEntry)

    @Insert
    fun insertAll(vararg users: GpsEntry)

    @Delete
    fun delete(user: GpsEntry)
}