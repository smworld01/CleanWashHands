package com.hands.clean.function.room

import android.content.Context
import androidx.room.Room

object DB {
    lateinit var db: AppDatabase

    fun getInstance(context: Context? = null): AppDatabase {
        if (!this::db.isInitialized) {
            db = useDatabase(context!!)
        }
        return db
    }

    private fun useDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "info"
            ).build()
    }
}
