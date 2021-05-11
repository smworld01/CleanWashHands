package com.hands.clean.function.room.entrys

import androidx.room.*

@Dao
interface GpsDao {
    @Query("SELECT * FROM GpsEntry")
    fun getAll(): List<GpsEntry>

    @Query("SELECT * FROM GpsEntry WHERE uid IN (:unitIds)")
    fun loadAllByIds(unitIds: IntArray): List<GpsEntry>

    @Query("SELECT * FROM GpsEntry WHERE requestId = :requestId")
    fun findByRequestId(requestId: String): GpsEntry?

    @Query("SELECT * FROM GpsEntry WHERE name = :name")
    fun findByName(name: String): GpsEntry?

    @Query("UPDATE GpsEntry SET notification = :bool WHERE requestId = :requestId")
    fun changeNotificationByRequestId(requestId: String, bool: Boolean)

    @Query("UPDATE GpsEntry SET name = :name WHERE RequestId = :RequestId")
    fun changeNameByRequestId(RequestId: String, name: String)

    @Query("UPDATE GpsEntry SET radius = :radius WHERE RequestId = :RequestId")
    fun changeRadiusByRequestId(RequestId: String, radius: Float)

    @Update
    fun updateAll(vararg gpsEntry: GpsEntry)

    @Insert
    fun insertAll(vararg users: GpsEntry)

    @Delete
    fun delete(user: GpsEntry)
}