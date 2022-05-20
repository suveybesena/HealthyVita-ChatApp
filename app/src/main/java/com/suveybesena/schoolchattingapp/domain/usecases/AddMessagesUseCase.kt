package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.MessageModel
import com.suveybesena.schoolchattingapp.di.IoDispatcher
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseFirestoreRepository
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseStorageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AddMessagesUseCase @Inject constructor(
    private var firebaseFirestoreRepository: FirebaseFirestoreRepository,
    var firebaseStorageRepository: FirebaseStorageRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(messageModel: MessageModel) = flow {
        emit(Resource.Loading)
        try {
            if (messageModel.imageUrl != null) {
                withContext(NonCancellable) {
                    firebaseStorageRepository.addMessageImageToStorage(
                        messageModel.imageUrl!!,
                        messageModel.senderId
                    ).let { image ->
                        firebaseFirestoreRepository.addMessagesToFirebase(messageModel, image)
                    }
                }
            } else {
                firebaseFirestoreRepository.addMessagesToFirebase(messageModel, "")
            }
            emit(Resource.Success(null))
        } catch (e: Exception) {
            println(e.localizedMessage)
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}