package com.example.speakez.data.data_source

import androidx.room.*
import com.example.speakez.data.model.PracticeSession
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Query("SELECT * FROM practice_sessions ORDER BY timestamp DESC")
    fun getAllSessions(): Flow<List<PracticeSession>>

    @Query("SELECT * FROM practice_sessions WHERE id = :id")
    suspend fun getSessionById(id: String): PracticeSession?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSession(session: PracticeSession)

    @Delete
    suspend fun deleteSession(session: PracticeSession)
}
