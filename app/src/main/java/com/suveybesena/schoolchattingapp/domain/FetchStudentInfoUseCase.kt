package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.forum.StudentModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FetchStudentInfoUseCase @Inject constructor(val repository: Repository) {
    private lateinit var list: StudentModel

    suspend fun invoke(currentUserId: String) = flow {
        emit(Resource.Loading)
        try {
            repository.fetchCurrentStudentInfo(currentUserId).forEach { document ->
                val studentName = document.get("userName") as String
                val studentImage = document.get("userImage") as String
                val studentId = document.get("userId") as String
                list = StudentModel(studentName, studentImage, studentId)
            }
            emit(Resource.Success(list))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)


}