package com.example.speakez.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "practice_sessions")
data class PracticeSession(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val timestamp: Long,
    val userGoal: UserGoal,
    val question: String,
    val userAnswer: String,
    val analysisResult: AnalysisResult
)
