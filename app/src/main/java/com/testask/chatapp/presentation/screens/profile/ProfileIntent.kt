package com.testask.chatapp.presentation.screens.profile

import com.testask.chatapp.domain.models.UserProfile

sealed class ProfileIntent {
    object LoadProfile : ProfileIntent()
    data class ProfileLoaded(val profile: UserProfile) : ProfileIntent()
    data class LoadingFailed(val error: String) : ProfileIntent()
}