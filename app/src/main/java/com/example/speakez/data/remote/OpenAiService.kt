package com.example.speakez.data.remote

import com.example.speakez.domain.model.AnalysisResult
import com.example.speakez.domain.model.UserGoal
import com.example.speakez.domain.service.LLMService
import javax.inject.Inject

class OpenAiService @Inject constructor(
    private val api: OpenAiApi
) : LLMService {
    override suspend fun generateNextQuestion(history: List<String>, userGoal: UserGoal): String {
        // In a real implementation, this would call api.generateChatCompletion(...)
        return "That's interesting. Can you tell me more about how you handled that situation?"
    }

    override suspend fun analyzeTranscription(transcription: String): AnalysisResult {
        // In a real implementation, this would call api.analyzeTranscription(...)
        return AnalysisResult(
            sessionId = "temp_id",
            confidenceScore = 0.85f,
            clarityScore = 0.9f,
            paceScore = 0.8f,
            contentScore = 0.75f,
            grammarScore = 0.95f,
            wpm = 140,
            fillerWordCount = 3,
            transcription = transcription,
            suggestions = listOf("Try to use more specific examples.", "Watch your pace during the conclusion."),
            starMethodCheck = true,
            idealAnswer = "A more structured answer would be..."
        )
    }
}
