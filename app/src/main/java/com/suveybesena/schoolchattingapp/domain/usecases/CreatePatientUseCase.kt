package com.suveybesena.schoolchattingapp.domain.usecases

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.di.IoDispatcher
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseAuthRepository
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseFirestoreRepository
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseStorageRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CreatePatientUseCase @Inject constructor(
    private val firebaseFirestoreRepository: FirebaseFirestoreRepository,
    private var firebaseStorageRepository: FirebaseStorageRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(registerModel: RegisterModel) = flow {
        emit(Resource.Loading)
        try {
            firebaseAuthRepository.signUpWithEmail(registerModel).let {
                firebaseAuthRepository.getCurrentUserId()?.let { id ->
                    firebaseStorageRepository.addImageToStorageForDoctors(
                        registerModel.userPhoto,
                        id
                    )
                        .let { image ->
                            firebaseFirestoreRepository.addPatientInfoToFirebase(
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
    }.flowOn(ioDispatcher)
}