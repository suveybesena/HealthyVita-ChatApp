package com.suveybesena.schoolchattingapp.domain.usecases


import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumDetailModel
import com.suveybesena.schoolchattingapp.di.IoDispatcher
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseFirestoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddForumAnswerUseCase @Inject constructor(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend operator fun invoke(forumAnswersModel: ForumDetailModel) = flow {
        emit(Resource.Loading)
        try {
            val answers = firebaseFirestoreRepository.addForumAnswersToFirebase(forumAnswersModel)
            emit(Resource.Success(answers))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}
