package com.example.speakez.data.repository

import com.example.speakez.domain.model.AnalysisResult
import com.example.speakez.domain.model.UserGoal
import com.example.speakez.domain.repository.SimulationRepository
import com.example.speakez.domain.service.LLMService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SimulationRepositoryImpl @Inject constructor(
    private val llmService: LLMService
) : SimulationRepository {

    override fun getNextQuestion(userGoal: UserGoal, currentTranscript: String): Flow<String> = flow {
        // In a real app, this would involve LLM processing of the transcript
        val question = llmService.generateNextQuestion(listOf(currentTranscript), userGoal)
        emit(question)
    }

    override fun analyzeAnswer(transcript: String): Flow<AnalysisResult> = flow {
        val result = llmService.analyzeTranscription(transcript)
        emit(result)
    }
}
