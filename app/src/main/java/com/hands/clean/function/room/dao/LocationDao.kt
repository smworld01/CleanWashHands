package com.hands.clean.function.room.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.hands.clean.function.room.entry.GpsEntry
import com.hands.clean.function.room.entry.LocationEntry

@Dao
interface LocationDao {
    @Query("SELECT * FROM LocationEntry")
    fun getAll(): List<LocationEntry>

    @Query("DELETE FROM LocationEntry")
    fun deleteAll()

    @Query("SELECT * FROM LocationEntry ORDER BY uid DESC LIMIT 1")
    fun getLast(): LocationEntry?

    @Update
    fun updateAll(vararg entries: LocationEntry)

    @Insert
    fun insertAll(vararg entries: LocationEntry) // todo maximum 100 limit

    @Delete
    fun delete(entry: LocationEntry)
}