package com.hands.clean.function.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hands.clean.function.room.entry.DateCount
import com.hands.clean.function.room.entry.WashEntry
import java.util.*

@Dao
interface WashDao {
    @Query("SELECT * FROM WashEntry")
    fun getAll(): List<WashEntry>

    @Query("SELECT * FROM WashEntry ORDER BY uid DESC")
    fun getAllDescByLiveData(): LiveData<List<WashEntry>>

    @Query("SELECT * FROM WashEntry ORDER BY uid DESC LIMIT 1")
    fun getLatest(): WashEntry?

    @Query("SELECT * FROM WashEntry WHERE uid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<WashEntry>

    @Query("SELECT * FROM WashEntry WHERE uid = :uid")
    fun findById(uid: Int): WashEntry

    @Query("SELECT * FROM WashEntry WHERE DATE(date) = DATE(:date)")
    fun findByDate(date: Date): List<WashEntry>

    @Query("SELECT COUNT(*) FROM WashEntry WHERE DATE(date) = DATE(:date)")
    fun countDate(date: Date): Int

    @Query("SELECT COUNT(*) FROM WashEntry WHERE DATE(date) = DATE('now','localtime')")
    fun countToday(): LiveData<Int>

    @Query("SELECT DATE('now')")
    fun now(): String

    @Query("SELECT DATE(date), COUNT(*) FROM WashEntry GROUP BY DATE(date) ORDER BY date DESC")
    fun countByDate(): LiveData<MutableList<DateCount>>

    @Update
    fun update(vararg washEntries: WashEntry)

    @Insert
    fun insertAll(vararg users: WashEntry): List<Long>

    @Delete
    fun delete(user: WashEntry)
}