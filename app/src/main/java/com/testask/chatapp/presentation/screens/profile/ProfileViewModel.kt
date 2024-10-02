package com.testask.chatapp.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testask.chatapp.domain.repository.UserProfileRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val repository: UserProfileRepository) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileState(isLoading = true))
    val uiState: StateFlow<ProfileState> = _uiState

    init {
        handleIntent(ProfileIntent.LoadProfile)
    }

    fun handleIntent(intent: ProfileIntent) {
        when (intent) {
            is ProfileIntent.LoadProfile -> {
                viewModelScope.launch {
                    try {
//                        val profile = repository.fetchProfileFromApi()
//                        _uiState.value = ProfileState(userProfile = profile, isLoading = false)
                    } catch (e: Exception) {
                        _uiState.value = ProfileState(errorMessage = e.message, isLoading = false)
                    }
                }
            }

            is ProfileIntent.LoadingFailed -> TODO()
            is ProfileIntent.ProfileLoaded -> TODO()
        }
    }
}