package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.di.IoDispatcher
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseFirestoreRepository
import com.suveybesena.schoolchattingapp.presentation.forum.UserModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchPatientInfoUseCase @Inject constructor(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    private lateinit var list: UserModel
    suspend fun invoke(currentUserId: String) = flow {
        emit(Resource.Loading)
        try {
            firebaseFirestoreRepository.fetchCurrentPatientInfo(currentUserId).forEach { document ->
                val patientName = document.get("userName") as String
                val patientImage = document.get("userImage") as String
                val patientId = document.get("userId") as String
                val patientMail = document.get("userMail") as String
                list = UserModel(patientName, patientImage, patientId, patientMail)
            }
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(ioDispatcher)
}