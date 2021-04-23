package com.hands.clean.function.room

import android.content.Context
import androidx.room.Room

lateinit var db: AppDatabase

fun useDatabase(context: Context): AppDatabase {
    return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "database-name"
        ).build()
}