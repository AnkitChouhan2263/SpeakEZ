package com.example.speakez.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.speakez.domain.model.CommunicationGoal
import java.util.Date

@Entity(tableName = "practice_sessions")
data class PracticeSession(
    @PrimaryKey val id: String,
    val timestamp: Date,
    val goal: CommunicationGoal,
    val transcript: String,
    val wpm: Int,
    val fillerWordCount: Int,
    val confidenceScore: Float,
    val clarityScore: Float,
    val paceScore: Float,
    val contentScore: Float,
    val grammarScore: Float,
    val audioPath: String?
)
