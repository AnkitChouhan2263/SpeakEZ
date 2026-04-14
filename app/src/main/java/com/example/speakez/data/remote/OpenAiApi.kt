package com.example.speakez.data.remote

import com.example.speakez.data.remote.dto.OpenApiRequest
import com.example.speakez.data.remote.dto.OpenApiResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenAiApi {
    @POST("v1/chat/completions")
    suspend fun generateChatCompletion(
        @Header("Authorization") apiKey: String,
        @Body request: OpenApiRequest
    ): OpenApiResponse
}
