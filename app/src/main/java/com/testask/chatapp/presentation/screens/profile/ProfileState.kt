package com.testask.chatapp.presentation.screens.profile

import com.testask.chatapp.domain.models.ProfileData
import com.testask.chatapp.domain.models.UserProfile

data class ProfileState(
    val userProfile: ProfileData? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)