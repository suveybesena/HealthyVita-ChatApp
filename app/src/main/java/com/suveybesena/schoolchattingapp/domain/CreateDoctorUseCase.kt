package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


class CreateDoctorUseCase @Inject constructor(private val repository: Repository) {

    suspend fun invoke(registerModel: RegisterModel, field: String) = flow {
        emit(Resource.Loading)
        try {
            repository.signUp(registerModel).let {
                repository.getCurrentUserId()?.let { id ->
                    repository.saveMediaToStorageForDoctors(registerModel.userPhoto, id)
                        .let { image ->
                            repository.saveInfoToFirestoreForTeachers(
                                registerModel.userName, id, image, registerModel.email, field
                            )
                        }
                }
            }
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(
        Dispatchers.IO
    )
}