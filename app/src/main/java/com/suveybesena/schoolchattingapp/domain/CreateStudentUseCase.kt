package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class CreateStudentUseCase @Inject constructor(val repository: Repository){

    suspend fun invoke(registerModel: RegisterModel) = flow {
        emit(Resource.Loading)
        try {
            repository.signUp(registerModel).let {
                repository.getCurrentUserId()?.let {id->
                    repository.saveMediaToStorageForStudents(registerModel.userPhoto, id)?.let { image->
                        repository.saveInfoToFirestoreForStudents(
                            id,image,registerModel.userName
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