package com.suveybesena.schoolchattingapp.presentation.teachers

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class FetchCurrentUserIdUseCase @Inject constructor(val repository: Repository) {

    suspend fun invoke(currentUserId: String) = flow {
        emit(Resource.Loading)
        try {
            val currentUserInfo = repository.fetchCurrentStudentInfo(currentUserId)
            emit(Resource.Success(currentUserInfo))

        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }

    }


}