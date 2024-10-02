package com.testask.chatapp.presentation.screens.chatdetail

data class ChatDetailState(
    val messages: List<String> = emptyList(),
    val messageText: String = ""
)
