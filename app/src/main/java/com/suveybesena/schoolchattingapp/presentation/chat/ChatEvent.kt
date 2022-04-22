package com.suveybesena.schoolchattingapp.presentation.chat

sealed class ChatEvent {
    data class AddMessageToFirebase(val messageModel: MessageModel) : ChatEvent()
    data class FetchMessage(val currentUserId: String, val receiverId: String): ChatEvent()
}
