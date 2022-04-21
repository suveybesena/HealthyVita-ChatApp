package com.suveybesena.schoolchattingapp.presentation.chat

data class ChatUiState(
    val isLoading: Boolean? = null,
    val messageList: List<MessageModel>? = null,
    val error: Boolean? = null,
    val isAdded: Boolean? = null
)