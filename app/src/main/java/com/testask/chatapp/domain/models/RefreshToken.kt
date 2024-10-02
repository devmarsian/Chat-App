package com.testask.chatapp.domain.models

data class RefreshToken(
    val access_token: String,
    val refresh_token: String,
    val user_id: Int
)