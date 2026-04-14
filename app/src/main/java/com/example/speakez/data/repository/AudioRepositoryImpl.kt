package com.example.speakez.data.repository

import android.media.MediaRecorder
import com.example.speakez.domain.repository.AudioRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

class AudioRepositoryImpl @Inject constructor() : AudioRepository {
    private var mediaRecorder: MediaRecorder? = null
    private var amplitudeJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.IO)

    private val _amplitudeFlow = MutableStateFlow(0f)
    override val amplitudeFlow: Flow<Float> = _amplitudeFlow

    private val _transcriptFlow = MutableStateFlow("")
    override val transcriptFlow: Flow<String> = _transcriptFlow

    private val _errorFlow = MutableStateFlow("")
    override val errorFlow: Flow<String> = _errorFlow

    override fun startRecording(outputFile: File) {
        mediaRecorder = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
            setAudioEncoder(MediaRecorder.AudioEncoder.AAC)
            setOutputFile(outputFile.absolutePath)
            try {
                prepare()
                start()
                startAmplitudePolling()
            } catch (e: Exception) {
                _errorFlow.value = e.message ?: "Failed to start recording"
            }
        }
    }

    private fun startAmplitudePolling() {
        amplitudeJob?.cancel()
        amplitudeJob = scope.launch {
            while (isActive) {
                val maxAmplitude = mediaRecorder?.maxAmplitude ?: 0
                // Normalize amplitude (maxAmplitude is usually up to 32767)
                _amplitudeFlow.value = (maxAmplitude / 32767f)
                delay(100) // 10 samples per second for the visualizer
            }
        }
    }

    override fun stopRecording() {
        amplitudeJob?.cancel()
        mediaRecorder?.apply {
            try {
                stop()
            } catch (e: Exception) {
                // Handle cases where stop is called too soon after start
            }
            release()
        }
        mediaRecorder = null
    }
}
