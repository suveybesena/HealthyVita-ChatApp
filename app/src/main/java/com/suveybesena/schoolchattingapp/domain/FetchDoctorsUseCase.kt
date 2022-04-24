package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.doctors.DoctorFeedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchDoctorsUseCase @Inject constructor(val repository: Repository) {

    suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val doctorList = ArrayList<DoctorFeedModel>()
            repository.fetchDoctorData().forEach { document ->
                val doctorName = document.get("userName") as String
                val doctorImage = document.get("userImage") as String
                val doctorField = document.get("field") as String
                val doctorId = document.get("userId") as String
                val list = DoctorFeedModel(doctorId, doctorName, doctorImage, doctorField)
                doctorList.add(list)
            }
            emit(Resource.Success(doctorList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}