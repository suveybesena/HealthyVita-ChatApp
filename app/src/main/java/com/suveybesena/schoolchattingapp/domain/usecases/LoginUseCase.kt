package com.suveybesena.schoolchattingapp.domain.usecases


import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.di.IoDispatcher
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseAuthRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: FirebaseAuthRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {

    suspend fun invoke(signInInfo: LoginModel) = flow {
        emit(Resource.Loading)
        try {
            repository.signIn(signInInfo)?.let {
                emit(Resource.Success(null))
            }
        } catch (e: Exception) {
        }
    }.flowOn(ioDispatcher)
}