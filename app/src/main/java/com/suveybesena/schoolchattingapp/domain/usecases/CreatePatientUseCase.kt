package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.domain.repositories.FirebaseAuthRepository
import com.suveybesena.schoolchattingapp.domain.repositories.FirebaseFirestoreRepository
import com.suveybesena.schoolchattingapp.domain.repositories.FirebaseStorageRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreatePatientUseCase @Inject constructor(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
    private var firebaseStorageRepository: FirebaseStorageRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
) {

    suspend fun invoke(registerModel: RegisterModel) = flow {
        emit(Resource.Loading)
        try {
            firebaseAuthRepository.signUp(registerModel).let {
                firebaseAuthRepository.getCurrentUserId()?.let { id ->
                    firebaseStorageRepository.saveMediaToStorageForPatients(
                        registerModel.userPhoto,
                        id
                    )
                        .let { image ->
                            firebaseFirestoreRepository.saveInfoToFirestoreForPatients(
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