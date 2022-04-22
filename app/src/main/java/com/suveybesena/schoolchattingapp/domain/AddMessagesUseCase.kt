package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.chat.MessageModel
import com.suveybesena.schoolchattingapp.presentation.forum.ForumModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class AddMessagesUseCase @Inject constructor(var repository: Repository) {
    suspend fun invoke(messageModel: MessageModel) = flow {
        emit(Resource.Loading)
        try {
            val addMessage = repository.saveMessageToFirestore(messageModel)
            emit(Resource.Success(addMessage))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun invoke(forumModel: ForumModel) = flow {
        emit(Resource.Loading)
        try {
            val forumMessages = repository.saveForumMessagesToFirebase(forumModel)
            emit(Resource.Success(forumMessages))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}