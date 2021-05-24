package com.hands.clean.function.room.dao

import androidx.room.*
import com.hands.clean.function.room.entry.LocationEntry

@Dao
interface LocationDao {
    @Query("SELECT * FROM LocationEntry")
    fun getAll(): List<LocationEntry>

    @Query("DELETE FROM LocationEntry")
    fun deleteAll()

    @Query("SELECT * FROM LocationEntry ORDER BY uid DESC LIMIT 1")
    fun getLast(): LocationEntry?

    @Query("""DELETE FROM LocationEntry 
        WHERE uid < (select min(uid) from (
        select uid
        from LocationEntry
        order by uid desc limit 100) a)""")
    fun deleteOverData()

    @Query("SELECT count(*) FROM LocationEntry")
    fun getCount() : Int

    @Update
    fun updateAll(vararg entries: LocationEntry)

    @Insert
    fun insertAll(vararg entries: LocationEntry) // todo maximum 100 limit

    @Delete
    fun delete(entry: LocationEntry)
}