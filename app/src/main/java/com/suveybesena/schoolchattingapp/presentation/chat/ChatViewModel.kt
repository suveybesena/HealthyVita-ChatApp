package com.suveybesena.schoolchattingapp.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.domain.AddMessagesUseCase
import com.suveybesena.schoolchattingapp.domain.FetchMessagesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val addMessagesUseCase: AddMessagesUseCase,
    private val fetchMessagesUseCase: FetchMessagesUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(ChatUiState())
    val _uiState: StateFlow<ChatUiState> = uiState.asStateFlow()

    fun handleEvent(event: ChatEvent) {
        when (event) {
            is ChatEvent.AddMessageToFirebase -> {
                addMessages(event.messageModel)
            }
            is ChatEvent.FetchMessage -> {
                fetchMessages(event.currentUserId, event.receiverId)
            }
        }
    }

    private fun addMessages(messages: MessageModel) {
        viewModelScope.launch {
            addMessagesUseCase.invoke(messages).collect {}
        }
    }

    private fun fetchMessages(currentUserId: String, receiverId: String) {
        viewModelScope.launch {
            fetchMessagesUseCase.invoke(currentUserId, receiverId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(messageList = resultState.data as List<FetchedMessageModel>)
                        }
                    }
                }
            }
        }
    }
}