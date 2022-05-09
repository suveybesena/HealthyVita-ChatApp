package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumModel
import com.suveybesena.schoolchattingapp.domain.repositories.FirebaseFirestoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchForumMessagesUseCase @Inject constructor(private val firebaseFirestoreRepository: FirebaseFirestoreRepository) {

    suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val forumList = ArrayList<ForumModel>()
            firebaseFirestoreRepository.fetchForumMessagesFromFirebase().forEach { document ->
                val forumMessages = document.get("forumMessage") as String
                val userId = document.get("userId") as String
                val time = document.get("time") as Long
                val messageId = document.get("messageId") as String
                val userImage = document.get("userImage") as String
                val userName = document.get("userName") as String
                val listForum =
                    ForumModel(forumMessages, userId, time, messageId, userImage, userName)
                forumList.add(listForum)
                emit(Resource.Success(forumList))
            }
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
            println(e.localizedMessage)
        }
    }.flowOn(Dispatchers.IO)
}