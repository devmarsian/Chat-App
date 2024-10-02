package com.testask.chatapp.presentation.screens.authorization

sealed class AuthorizationIntent {
    data class EnterPhoneNumber(val phoneNumber: String) : AuthorizationIntent()
    data class SelectCountry(val country: String) : AuthorizationIntent()
    data object GetCode : AuthorizationIntent()
    data class VerifyCode(val code: String) : AuthorizationIntent()
    data class EnterVerificationCode(val code: String, val index: Int) : AuthorizationIntent()
    object ToggleDropdown : AuthorizationIntent()
}
