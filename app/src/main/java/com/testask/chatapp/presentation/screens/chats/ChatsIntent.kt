package com.testask.chatapp.presentation.screens.chats

sealed class ChatsIntent {
    data class OpenChat(val chat: Chat): ChatsIntent()
}