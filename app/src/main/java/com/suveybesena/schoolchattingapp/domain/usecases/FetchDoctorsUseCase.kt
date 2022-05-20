package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.di.IoDispatcher
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseFirestoreRepository
import com.suveybesena.schoolchattingapp.presentation.doctors.DoctorFeedModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchDoctorsUseCase @Inject constructor(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val doctorList = ArrayList<DoctorFeedModel>()
            firebaseFirestoreRepository.fetchDoctorInfo().forEach { document ->
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
    }.flowOn(ioDispatcher)
}