package com.suveybesena.schoolchattingapp.presentation.chat

sealed class ChatEvent {
    data class AddMessageToFirebase(val messageModel: MessageModel,val currentUserId : String?) : ChatEvent()
    data class FetchMessages ( val receiverId : String) : ChatEvent()
}
