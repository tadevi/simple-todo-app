package com.example.data.db.converters

import androidx.room.TypeConverter
import java.sql.Timestamp
import java.util.*

class CalendarConverter {
    @TypeConverter
    fun fromTimestamp(calendar: Calendar?): Long {
        calendar?.let {
            return calendar.timeInMillis
        }
        return -1
    }

    @TypeConverter
    fun toTimestamp(timestamp: Long): Calendar? {
        if (timestamp == -1L) {
            return null
        }
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = timestamp
        return calendar
    }
}