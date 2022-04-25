package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.forum.forumfeed.ForumModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchForumMessagesUseCase @Inject constructor(val repository: Repository) {

    suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val forumList = ArrayList<ForumModel>()
            repository.fetchForumMessagesFromFirebase().forEach { document ->
                val forumMessages = document.get("forumMessage") as String
                val userId = document.get("userId") as String
                val time = document.get("time") as Long
                val messageId = document.get("messageId") as String
                val userImage = document.get("userImage") as String
                val userName = document.get("userName") as String
                val answerList = document.get("forumAnswers") as ArrayList<String>
                val listForum =
                    ForumModel(forumMessages, userId, time, messageId, userImage, userName,answerList)
                forumList.add(listForum)
                emit(Resource.Success(forumList))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}