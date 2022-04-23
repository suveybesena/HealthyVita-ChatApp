package com.suveybesena.schoolchattingapp.presentation.forum

data class ForumState(
    val currentStudentInfo: StudentModel? = null,
    val forumInfo: List<ForumModel>? = null,
    val isLoading: Boolean? = false,
    val error: String? = null
)
