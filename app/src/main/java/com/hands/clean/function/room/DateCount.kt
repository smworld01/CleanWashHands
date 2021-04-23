package com.hands.clean.function.room

import androidx.room.ColumnInfo
import androidx.room.Entity


@Entity
class DateCount(
        @ColumnInfo(name = "DATE(date)") val date: String,
        @ColumnInfo(name = "COUNT(*)") val count: String,
) {
    override fun toString(): String {
        return "DateCount($date, $count)"
    }
}