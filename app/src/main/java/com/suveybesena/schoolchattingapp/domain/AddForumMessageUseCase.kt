package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.forum.ForumModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class AddForumMessageUseCase @Inject constructor(val repository: Repository) {

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