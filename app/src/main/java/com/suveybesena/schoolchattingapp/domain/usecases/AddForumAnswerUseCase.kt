package com.suveybesena.schoolchattingapp.domain.usecases


import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumDetailModel
import com.suveybesena.schoolchattingapp.domain.repositories.FirebaseFirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddForumAnswerUseCase @Inject constructor(private val firebaseFirestoreRepository: FirebaseFirestoreRepository) {

    suspend operator fun invoke(forumAnswersModel: ForumDetailModel) = flow {
        emit(Resource.Loading)
        try {
            val answers = firebaseFirestoreRepository.addForumAnswersToFirebase(forumAnswersModel)
            emit(Resource.Success(answers))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}