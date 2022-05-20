package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumModel
import com.suveybesena.schoolchattingapp.di.IoDispatcher
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseFirestoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddForumMessageUseCase @Inject constructor(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(forumModel: ForumModel) = flow {
        emit(Resource.Loading)
        try {
            val forumMessages = firebaseFirestoreRepository.saveForumMessagesToFirebase(forumModel)
            emit(Resource.Success(forumMessages))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}