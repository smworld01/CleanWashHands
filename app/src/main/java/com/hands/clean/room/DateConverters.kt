package com.hands.clean.room

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class DateConverters {
        @TypeConverter
        fun dateToString(date: Date?): String? {
            val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            return format.format(date)
        }
    }