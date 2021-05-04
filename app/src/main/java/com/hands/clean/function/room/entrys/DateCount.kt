package com.hands.clean.function.room.entrys

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
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

    companion object {
        object DateCountDiffCallback : DiffUtil.ItemCallback<DateCount>() {
            override fun areItemsTheSame(oldItem: DateCount, newItem: DateCount): Boolean {
                return oldItem.date == newItem.date
            }

            override fun areContentsTheSame(oldItem: DateCount, newItem: DateCount): Boolean {
                return oldItem.count == newItem.count
            }
        }
    }
}