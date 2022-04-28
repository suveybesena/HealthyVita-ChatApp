package com.suveybesena.schoolchattingapp.domain


import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginUseCase @Inject constructor(val repository: Repository) {
    suspend fun invoke(signInInfo: LoginModel) = flow {
        emit(Resource.Loading)
        try {
            repository.signIn(signInInfo)?.let {
                emit(Resource.Success(null))
            }
        } catch (e: Exception) {
        }
    }.flowOn(Dispatchers.IO)
}