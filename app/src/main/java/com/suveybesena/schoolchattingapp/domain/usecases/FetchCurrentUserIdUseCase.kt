package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.domain.repositories.FirebaseFirestoreRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FetchCurrentUserIdUseCase @Inject constructor(private val firebaseFirestoreRepository: FirebaseFirestoreRepository) {

    suspend fun invoke(currentUserId: String) = flow {
        emit(Resource.Loading)
        try {
            val currentUserInfo = firebaseFirestoreRepository.fetchCurrentPatientInfo(currentUserId)
            emit(Resource.Success(currentUserInfo))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }
}