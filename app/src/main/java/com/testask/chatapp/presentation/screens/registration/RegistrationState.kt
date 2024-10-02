package com.testask.chatapp.presentation.screens.registration

data class RegistrationState(
    val phoneNumber: String = "",
    val name: String = "",
    val username: String = "",
    val isPhoneNumberValid: Boolean = false,
    val isNameValid: Boolean = false,
    val isUsernameValid: Boolean = false,
    val isLoading: Boolean = false,
    val registrationCompleted: Boolean = false,
    val errorMessage: String? = null
)