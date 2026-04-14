package com.example.speakez.domain.repository

import com.example.speakez.domain.model.Session
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getAllSessions(): Flow<List<Session>>
    suspend fun getSessionById(id: String): Session?
    suspend fun saveSession(session: Session)
    suspend fun deleteSession(session: Session)
}
