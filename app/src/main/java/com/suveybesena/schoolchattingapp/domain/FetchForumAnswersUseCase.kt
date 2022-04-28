package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.forum.forumdetail.ForumDetailModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class FetchForumAnswersUseCase @Inject constructor(var repository: Repository) {

    suspend  fun invoke(messageId: String) = flow {
        emit(Resource.Loading)
        try {
            val answerList = ArrayList<ForumDetailModel>()
            repository.fetchForumAnswersModel(messageId).forEach { document ->
                val message = document.get("answers") as String
                val time = document.get("time") as String
                val answer = ForumDetailModel(message, time)
                answerList.add(answer)
            }
            emit(Resource.Success(answerList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}