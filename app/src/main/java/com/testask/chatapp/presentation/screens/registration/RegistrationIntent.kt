package com.testask.chatapp.presentation.screens.registration

import com.testask.chatapp.domain.models.UserCreation
import com.testask.chatapp.presentation.screens.authorization.AuthorizationIntent

sealed class RegistrationIntent {
    data class EnterName(val name: String) : RegistrationIntent()
    data class EnterUserName(val username: String) : RegistrationIntent()
    data object SubmitRegistration : RegistrationIntent()
}