package com.testask.chatapp.presentation.screens.chatdetail

sealed class ChatDetailIntent {
    data class UpdateMessageText(val text: String) : ChatDetailIntent()
    object SendMessage : ChatDetailIntent()
}