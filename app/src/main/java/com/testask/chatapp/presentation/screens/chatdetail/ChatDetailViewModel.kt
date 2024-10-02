package com.testask.chatapp.presentation.screens.chatdetail

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class ChatDetailViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(ChatDetailState())
    val uiState: StateFlow<ChatDetailState> = _uiState

    fun handleIntent(intent: ChatDetailIntent) {
        when (intent) {
            is ChatDetailIntent.UpdateMessageText -> {
                _uiState.update { it.copy(messageText = intent.text) }
            }
            is ChatDetailIntent.SendMessage -> {
                val currentText = _uiState.value.messageText
                if (currentText.isNotBlank()) {
                    _uiState.update {
                        it.copy(
                            messages = it.messages + currentText,
                            messageText = ""
                        )
                    }
                }
            }
        }
    }
}