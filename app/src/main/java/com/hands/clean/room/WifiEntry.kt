package com.hands.clean.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WifiEntry(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "notification") val notification: Boolean,
)
