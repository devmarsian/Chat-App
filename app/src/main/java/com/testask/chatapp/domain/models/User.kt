package com.testask.chatapp.domain.models

data class User(
    val access_token: String,
    val is_user_exists: Boolean,
    val refresh_token: String,
    val user_id: Int
)