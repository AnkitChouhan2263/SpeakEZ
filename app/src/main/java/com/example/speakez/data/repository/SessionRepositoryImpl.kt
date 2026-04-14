package com.example.speakez.data.repository

import com.example.speakez.data.data_source.SessionDao
import com.example.speakez.data.model.PracticeSession
import com.example.speakez.domain.model.AnalysisResult
import com.example.speakez.domain.model.Session
import com.example.speakez.domain.repository.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(
    private val dao: SessionDao
) : SessionRepository {

    override fun getAllSessions(): Flow<List<Session>> {
        return dao.getAllSessions().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getSessionById(id: String): Session? {
        return dao.getSessionById(id)?.toDomain()
    }

    override suspend fun saveSession(session: Session) {
        dao.insertSession(session.toEntity())
    }

    override suspend fun deleteSession(session: Session) {
        dao.deleteSession(session.toEntity())
    }

    private fun PracticeSession.toDomain(): Session {
        return Session(
            id = id,
            timestamp = timestamp,
            goal = goal,
            audioUri = audioPath,
            analysisResult = AnalysisResult(
                sessionId = id,
                confidenceScore = confidenceScore,
                clarityScore = clarityScore,
                paceScore = paceScore,
                contentScore = contentScore,
                grammarScore = grammarScore,
                wpm = wpm,
                fillerWordCount = fillerWordCount,
                transcription = transcript,
                suggestions = emptyList(), // Simplified for now
                starMethodCheck = false, // Simplified for now
                idealAnswer = null // Simplified for now
            )
        )
    }

    private fun Session.toEntity(): PracticeSession {
        return PracticeSession(
            id = id,
            timestamp = timestamp,
            goal = goal,
            transcript = analysisResult?.transcription ?: "",
            wpm = analysisResult?.wpm ?: 0,
            fillerWordCount = analysisResult?.fillerWordCount ?: 0,
            confidenceScore = analysisResult?.confidenceScore ?: 0f,
            clarityScore = analysisResult?.clarityScore ?: 0f,
            paceScore = analysisResult?.paceScore ?: 0f,
            contentScore = analysisResult?.contentScore ?: 0f,
            grammarScore = analysisResult?.grammarScore ?: 0f,
            audioPath = audioUri
        )
    }
}
