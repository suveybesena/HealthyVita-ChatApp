package com.suveybesena.schoolchattingapp.domain

import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.repository.Repository
import com.suveybesena.schoolchattingapp.presentation.teachers.TeacherFeedModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class FetchTeacherInfoUseCase @Inject constructor(val repository: Repository) {

    suspend fun invoke() = flow {
        emit(Resource.Loading)
        try {
            val teacherList = ArrayList<TeacherFeedModel>()
            repository.fetchTeacherData().forEach { document ->
                val teacherName = document.get("userName") as String
                val teacherImage = document.get("userImage") as String
                val teacherField = document.get("field") as String
                val teacherId = document.get("userId") as String
                val list = TeacherFeedModel(teacherId, teacherName, teacherImage, teacherField)
                teacherList.add(list)
            }
            emit(Resource.Success(teacherList))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage))
        }
    }.flowOn(Dispatchers.IO)
}