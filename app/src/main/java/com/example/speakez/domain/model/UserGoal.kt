package com.example.speakez.domain.model

data class UserGoal(
    val goal: CommunicationGoal,
    val target: String,
    val skills: List<String>
)
