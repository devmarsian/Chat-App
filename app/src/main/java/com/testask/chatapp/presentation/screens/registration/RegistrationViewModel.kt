package com.testask.chatapp.presentation.screens.registration

import androidx.lifecycle.ViewModel
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.testask.chatapp.common.navigation.NavigationEvent
import com.testask.chatapp.presentation.screens.authorization.AuthorizationIntent
import com.testask.chatapp.presentation.screens.authorization.AuthorizationState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RegistrationViewModel: ViewModel() {

    private val _registrationState = MutableStateFlow(RegistrationState())
    val registrationState: StateFlow<RegistrationState> = _registrationState

    init {
        _registrationState.value = RegistrationState()
    }

    fun onNameEntered(name: String) {
        _registrationState.value = _registrationState.value.copy(
            name = name,
            isNameValid = validateName(name)
        )
    }

    fun onUsernameEntered(username: String) {
        _registrationState.value = _registrationState.value.copy(
            username = username,
            isUsernameValid = validateUsername(username)
        )
    }


    private fun validateName(name: String): Boolean {
        return name.isNotEmpty()
    }

    private fun validateUsername(username: String): Boolean {
        val regex = "^[A-Za-z0-9-_]{3,16}$".toRegex()
        return regex.matches(username)
    }

    fun processIntent(intent: RegistrationIntent) {
        when(intent) {
            is RegistrationIntent.EnterName -> onNameEntered(intent.name)
            is RegistrationIntent.EnterUserName -> onUsernameEntered(intent.username)
            is RegistrationIntent.SubmitRegistration -> submitRegistration()
        }
    }

    private fun submitRegistration() {

    }
}