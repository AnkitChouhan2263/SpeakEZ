package com.example.speakez.domain.repository

import kotlinx.coroutines.flow.Flow
import java.io.File

interface AudioRepository {
    fun startRecording(outputFile: File)
    fun stopRecording()
    val transcriptFlow: Flow<String>
    val amplitudeFlow: Flow<Float>
    val errorFlow: Flow<String>
}
