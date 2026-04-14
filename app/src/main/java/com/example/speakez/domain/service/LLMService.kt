package com.example.speakez.domain.service

import com.example.speakez.domain.model.AnalysisResult
import com.example.speakez.domain.model.UserGoal

interface LLMService {
    suspend fun generateNextQuestion(history: List<String>, userGoal: UserGoal): String
    suspend fun analyzeTranscription(transcription: String): AnalysisResult
}
