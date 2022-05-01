package com.suveybesena.schoolchattingapp.domain


import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumDetailModel
import com.suveybesena.schoolchattingapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class AddForumAnswerUseCase @Inject constructor(val repository: Repository) {

    suspend operator fun invoke(forumAnswersModel: ForumDetailModel) = flow {
        emit(Resource.Loading)
        try {
            val answers = repository.addForumAnswersToFirebase(forumAnswersModel)
            emit(Resource.Success(answers))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}