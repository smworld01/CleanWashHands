package com.hands.clean.function.room

import android.content.Context
import android.util.Log
import androidx.room.Room

object DB {
    lateinit var db: AppDatabase

    fun init(context: Context) {
        db = useDatabase(context)
    }

    fun getInstance(): AppDatabase {
        return db
    }

    private fun useDatabase(context: Context): AppDatabase {
        return Room.databaseBuilder(
                    context,
                    AppDatabase::class.java, "info"
            ).allowMainThreadQueries()
            .build()
    }
}
