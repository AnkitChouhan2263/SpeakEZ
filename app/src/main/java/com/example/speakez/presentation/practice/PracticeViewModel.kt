package com.example.speakez.presentation.practice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.speakez.domain.model.AnalysisResult
import com.example.speakez.domain.model.CommunicationGoal
import com.example.speakez.domain.model.UserGoal
import com.example.speakez.domain.repository.SimulationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class PracticeEvent {
    data class OnTranscriptUpdate(val transcript: String) : PracticeEvent()
    data class OnAmplitudeUpdate(val amplitude: Float) : PracticeEvent()
    data class OnError(val error: String) : PracticeEvent()
    object OnRecordingStarted : PracticeEvent()
    object OnRecordingStopped : PracticeEvent()
    object OnPracticeAgain : PracticeEvent()
}

class PracticeViewModel(private val simulationRepository: SimulationRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PracticeScreenState())
    val uiState: StateFlow<PracticeScreenState> = _uiState.asStateFlow()

    private var recordingStartTime: Long = 0

    init {
        fetchNewQuestion()
    }

    private fun fetchNewQuestion() {
        viewModelScope.launch {
            // Default goal for now
            val defaultGoal = UserGoal(CommunicationGoal.INTERVIEW, "General", emptyList())
            val question = simulationRepository.getNextQuestion(defaultGoal, "").first()
            _uiState.update { it.copy(currentQuestion = question) }
        }
    }

    fun onEvent(event: PracticeEvent) {
        when (event) {
            is PracticeEvent.OnTranscriptUpdate -> _uiState.update { it.copy(userTranscript = event.transcript) }
            is PracticeEvent.OnAmplitudeUpdate -> {
                val updatedAmplitudes = (_uiState.value.amplitudes + event.amplitude).takeLast(100)
                _uiState.update { it.copy(amplitudes = updatedAmplitudes) }
            }
            is PracticeEvent.OnError -> _uiState.update { it.copy(error = event.error) }
            PracticeEvent.OnRecordingStarted -> {
                _uiState.update { it.copy(isRecording = true, analysisResult = null, userTranscript = "") }
                recordingStartTime = System.currentTimeMillis()
            }
            PracticeEvent.OnRecordingStopped -> {
                _uiState.update { it.copy(isRecording = false) }
                val recordingTimeSeconds = (System.currentTimeMillis() - recordingStartTime) / 1000.0
                val analysisResult = performLocalAnalysis(uiState.value.userTranscript, recordingTimeSeconds)
                _uiState.update { it.copy(analysisResult = analysisResult) }
            }
            PracticeEvent.OnPracticeAgain -> {
                _uiState.update { PracticeScreenState() }
                fetchNewQuestion()
            }
        }
    }

    private fun performLocalAnalysis(transcript: String, durationSeconds: Double): AnalysisResult {
        val words = transcript.split(Regex("\\s+")).filter { it.isNotBlank() }
        val wpm = if (durationSeconds > 1) ((words.size / durationSeconds) * 60).toInt() else 0
        val fillerWords = setOf("um", "uh", "like", "basically", "actually")
        val fillerCount = words.count { it.lowercase() in fillerWords }

        return AnalysisResult(
            sessionId = "session_${System.currentTimeMillis()}",
            confidenceScore = 0.7f, // Mock score for local analysis
            clarityScore = 0.8f,
            paceScore = 0.75f,
            contentScore = 0.6f,
            grammarScore = 0.85f,
            wpm = wpm,
            fillerWordCount = fillerCount,
            transcription = transcript,
            suggestions = listOf("Focus on reducing filler words."),
            starMethodCheck = transcript.contains("Situation", ignoreCase = true),
            idealAnswer = null
        )
    }

    companion object {
        fun provideFactory(
            simulationRepository: SimulationRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PracticeViewModel(simulationRepository) as T
            }
        }
    }
}
