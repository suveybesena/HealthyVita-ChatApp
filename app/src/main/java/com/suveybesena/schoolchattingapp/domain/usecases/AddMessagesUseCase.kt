package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.MessageModel
import com.suveybesena.schoolchattingapp.domain.repositories.FirebaseFirestoreRepository
import com.suveybesena.schoolchattingapp.domain.repositories.FirebaseStorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddMessagesUseCase @Inject constructor(private var firebaseFirestoreRepository: FirebaseFirestoreRepository, var firebaseStorageRepository: FirebaseStorageRepository) {

    suspend operator fun invoke(messageModel: MessageModel) = flow {
        emit(Resource.Loading)
        try {
            if (messageModel.imageUrl != null) {
                withContext(NonCancellable) {
                    firebaseStorageRepository.saveMediaToStorageForMessages(
                        messageModel.imageUrl!!,
                        messageModel.senderId
                    ).let { image ->
                        firebaseFirestoreRepository.saveMessageToFirestore(messageModel, image)
                    }
                }
            } else {
                firebaseFirestoreRepository.saveMessageToFirestore(messageModel, "")
            }
            emit(Resource.Success(null))
        } catch (e: Exception) {
            println(e.localizedMessage)
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}