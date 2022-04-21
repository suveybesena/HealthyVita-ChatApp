package com.suveybesena.schoolchattingapp.domain

import com.google.firebase.Timestamp
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.chat.MessageModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchMessageUseCase @Inject constructor(val repository: Repository) {

    suspend fun invoke(receiverId: String) = flow {
        emit(Resource.Loading)
        try {
            val messageList = ArrayList<MessageModel>()
            repository.fetchMessages(receiverId).forEach { document ->
                val message = document.get("message") as String
                val senderId = document.get("sender") as String
                val imageUrl = document.get("imageUrl") as String
                val timestamp = document.get("time") as Timestamp
                val receiverId = document.get("receiver") as String
                val list = MessageModel(message, senderId, imageUrl, timestamp, receiverId)
                messageList.add(list)
            }
            emit(Resource.Success(messageList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}