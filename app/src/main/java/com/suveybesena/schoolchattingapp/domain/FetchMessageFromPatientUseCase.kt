package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class FetchMessageFromPatientUseCase @Inject constructor(val repository: Repository) {

    suspend fun invoke(currentUserId: String) = flow {
        emit(Resource.Loading)
        try {
            val senderList = ArrayList<String>()
            repository.fetchPatientMessage(currentUserId).forEach { document ->
                val sender = document.get("sender") as String
                senderList.add(sender)
            }
            emit(Resource.Success(senderList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}