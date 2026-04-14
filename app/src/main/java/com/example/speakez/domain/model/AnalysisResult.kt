package com.example.speakez.domain.model

data class AnalysisResult(
    val sessionId: String,
    val confidenceScore: Float,
    val clarityScore: Float,
    val paceScore: Float,
    val contentScore: Float,
    val grammarScore: Float,
    val wpm: Int,
    val fillerWordCount: Int,
    val transcription: String,
    val suggestions: List<String>,
    val starMethodCheck: Boolean,
    val idealAnswer: String? = null
)
