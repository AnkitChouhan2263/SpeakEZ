package com.example.speakez.domain.repository

import kotlinx.coroutines.flow.Flow

interface TranscriptionRepository {
    fun startTranscribing()
    fun stopTranscribing()
    val transcriptFlow: Flow<String>
}
