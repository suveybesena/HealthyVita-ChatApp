package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumDetailModel
import com.suveybesena.schoolchattingapp.domain.repositories.FirebaseFirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchForumAnswersUseCase @Inject constructor(private val firebaseFirestoreRepository: FirebaseFirestoreRepository) {

    suspend fun invoke(messageId: String) = flow {
        emit(Resource.Loading)
        try {
            val answerList = ArrayList<ForumDetailModel>()
            firebaseFirestoreRepository.fetchForumAnswersModel(messageId).forEach { document ->
                val message = document.get("answers") as String
                val answer = ForumDetailModel("", message)
                answerList.add(answer)
            }
            emit(Resource.Success(answerList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}