package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreatePatientUseCase @Inject constructor(val repository: Repository) {

    suspend fun invoke(registerModel: RegisterModel) = flow {
        emit(Resource.Loading)
        try {
            repository.signUp(registerModel).let {
                repository.getCurrentUserId()?.let { id ->
                    repository.saveMediaToStorageForPatients(registerModel.userPhoto, id)
                        .let { image ->
                            repository.saveInfoToFirestoreForPatients(
                                id,
                                image,
                                registerModel.userName,
                                registerModel.email,
                                registerModel.password
                            )
                        }
                }
            }
            emit(Resource.Success(null))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}