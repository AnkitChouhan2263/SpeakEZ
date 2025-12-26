package com.example.speakez.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_goals")
data class UserGoal(
    @PrimaryKey val id: Int? = null,
    val goal: String,
    val target: String,
    val skills: List<String>
)
