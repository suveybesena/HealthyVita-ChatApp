package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.chat.MessageModel
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class AddMessagesUseCase @Inject constructor(var repository: Repository) {

    suspend fun invoke(messageModel: MessageModel, currentUserId: String) = flow {
        emit(Resource.Loading)
        try {
            val addMessage = repository.saveMessageToFirestore(messageModel, currentUserId)
            emit(Resource.Success(addMessage))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}