package com.hands.clean.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class WashEntry(
        @PrimaryKey(autoGenerate = true) val uid: Int,
        @ColumnInfo(name = "date") val date: String,
        @ColumnInfo(name = "type") val type: String,
        @ColumnInfo(name = "detail") val detail: String,
        @ColumnInfo(name = "wash") val wash: Boolean,
)
