package com.example.speakez.data.data_source

import androidx.room.TypeConverter
import com.example.speakez.domain.model.CommunicationGoal
import java.util.Date

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    @TypeConverter
    fun fromCommunicationGoal(goal: CommunicationGoal): String {
        return goal.name
    }

    @TypeConverter
    fun toCommunicationGoal(value: String): CommunicationGoal {
        return CommunicationGoal.valueOf(value)
    }
}
