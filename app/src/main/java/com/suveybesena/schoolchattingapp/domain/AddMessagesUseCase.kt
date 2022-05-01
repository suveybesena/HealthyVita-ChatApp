package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.MessageModel
import com.suveybesena.schoolchattingapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddMessagesUseCase @Inject constructor(var repository: Repository) {

    suspend operator fun invoke(messageModel: MessageModel) = flow {
        emit(Resource.Loading)
        try {
            if (messageModel.imageUrl != null) {
                withContext(NonCancellable) {
                    repository.saveMediaToStorageForMessages(
                        messageModel.imageUrl!!,
                        messageModel.senderId
                    ).let { image ->
                        repository.saveMessageToFirestore(messageModel, image)
                    }
                }
            } else {
                repository.saveMessageToFirestore(messageModel, "")
            }
            emit(Resource.Success(null))
        } catch (e: Exception) {
            println(e.localizedMessage)
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}