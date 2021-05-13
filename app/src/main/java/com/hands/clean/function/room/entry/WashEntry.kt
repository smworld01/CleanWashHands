package com.hands.clean.function.room.entry

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class WashEntry(
        @PrimaryKey(autoGenerate = true) val uid: Int = 0,
        @ColumnInfo(name = "date") val date: String,
        @ColumnInfo(name = "type") val type: String,
        @ColumnInfo(name = "detail") val detail: String,
        @ColumnInfo(name = "latitude") val latitude: Double? = null,
        @ColumnInfo(name = "longitude") val longitude: Double? = null,
        @ColumnInfo(name = "wash") val wash: Boolean = false,
)
