package com.example.speakez.domain.model

import java.util.Date

data class Session(
    val id: String,
    val timestamp: Date,
    val goal: CommunicationGoal,
    val audioUri: String?,
    val analysisResult: AnalysisResult? = null
)
