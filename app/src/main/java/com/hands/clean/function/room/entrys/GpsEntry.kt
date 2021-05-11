package com.hands.clean.function.room.entrys

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GpsEntry(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "requestId") val requestId: String,
        @ColumnInfo(name = "latitude") val latitude: Double,
        @ColumnInfo(name = "longitude") val longitude: Double,
        @ColumnInfo(name = "radius") val radius: Float,
        @ColumnInfo(name = "notification") val isNotification: Boolean,
)
