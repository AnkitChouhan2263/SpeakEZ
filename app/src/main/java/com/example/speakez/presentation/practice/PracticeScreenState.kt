package com.example.speakez.presentation.practice

import com.example.speakez.domain.model.AnalysisResult

data class PracticeScreenState(
    val currentQuestion: String = "",
    val userTranscript: String = "",
    val isRecording: Boolean = false,
    val amplitudes: List<Float> = emptyList(),
    val analysisResult: AnalysisResult? = null,
    val error: String? = null
)
