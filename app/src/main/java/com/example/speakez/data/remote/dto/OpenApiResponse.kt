package com.example.speakez.data.remote.dto

data class OpenApiResponse(
    val id: String,
    val choices: List<Choice>
)

data class Choice(
    val message: Message,
    val finish_reason: String
)
