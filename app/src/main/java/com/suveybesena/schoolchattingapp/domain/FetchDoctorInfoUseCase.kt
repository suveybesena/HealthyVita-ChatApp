package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.forum.UserModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchDoctorInfoUseCase @Inject constructor(val repository: Repository) {

    private lateinit var list: UserModel
    suspend fun invoke(currentUserId: String) = flow {
        emit(Resource.Loading)
        try {
            repository.fetchCurrentDoctorInfo(currentUserId).forEach { document ->
                val doctorName = document.get("userName") as String
                val doctorImage = document.get("userImage") as String
                val doctorId = document.get("userId") as String
                val userMail = document.get("userEmail") as String
                list = UserModel(doctorName, doctorImage, doctorId, userMail)
            }
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}