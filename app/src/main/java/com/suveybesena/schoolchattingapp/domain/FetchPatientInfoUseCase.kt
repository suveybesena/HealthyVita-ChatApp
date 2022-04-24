package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.forum.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchPatientInfoUseCase @Inject constructor(val repository: Repository) {
    private lateinit var list: UserModel

    suspend fun invoke(currentUserId: String) = flow {
        emit(Resource.Loading)
        try {
            repository.fetchCurrentPatientInfo(currentUserId).forEach { document ->
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
    }.flowOn(Dispatchers.IO)
}