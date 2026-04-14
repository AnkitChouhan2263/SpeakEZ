package com.example.speakez.domain.model

data class UserProfile(
    val goal: CommunicationGoal,
    val target: String, // e.g., "Tech Company", "MBA Interview"
    val skills: List<String>,
    val resumePath: String? = null,
    val xp: Int = 0,
    val streak: Int = 0
)
