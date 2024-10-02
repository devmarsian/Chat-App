package com.testask.chatapp.common.navigation

sealed class NavigationEvent {
    object NavigateToChat : NavigationEvent()
    data class NavigateToRegistration(val phoneNumber: String) : NavigationEvent()
}
