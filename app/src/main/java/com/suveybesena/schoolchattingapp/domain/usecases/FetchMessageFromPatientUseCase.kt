package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.di.IoDispatcher
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseFirestoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class FetchMessageFromPatientUseCase @Inject constructor(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(currentUserId: String) = flow {
        emit(Resource.Loading)
        try {
            val senderList = ArrayList<String>()
            firebaseFirestoreRepository.fetchPatientMessageFromFirebase(currentUserId)
                .forEach { document ->
                    val sender = document.get("sender") as String
                    senderList.add(sender)
                }
            emit(Resource.Success(senderList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}