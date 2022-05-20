package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.di.IoDispatcher
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseFirestoreRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchCurrentUserIdUseCase @Inject constructor(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(currentUserId: String) = flow {
        emit(Resource.Loading)
        try {
            val currentUserInfo = firebaseFirestoreRepository.fetchCurrentPatientInfo(currentUserId)
            emit(Resource.Success(currentUserInfo))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}