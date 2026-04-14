package com.example.speakez.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.speakez.domain.model.Session
import com.example.speakez.domain.repository.SessionRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

data class HomeUiState(
    val streakCount: Int = 0,
    val totalSessions: Int = 0,
    val averageWpm: Int = 0,
    val recentSessions: List<Session> = emptyList()
)

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val sessionRepository: SessionRepository
) : ViewModel() {

    val uiState: StateFlow<HomeUiState> = sessionRepository.getAllSessions()
        .map { sessions ->
            HomeUiState(
                streakCount = calculateStreak(sessions),
                totalSessions = sessions.size,
                averageWpm = if (sessions.isNotEmpty()) sessions.map { it.analysisResult?.wpm ?: 0 }.average().toInt() else 0,
                recentSessions = sessions.take(5)
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState()
        )

    private fun calculateStreak(sessions: List<Session>): Int {
        // Simplified streak calculation for now
        return if (sessions.isNotEmpty()) 1 else 0
    }
}
