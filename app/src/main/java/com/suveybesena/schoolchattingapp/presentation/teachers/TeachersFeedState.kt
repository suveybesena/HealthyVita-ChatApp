package com.suveybesena.schoolchattingapp.presentation.teachers

data class TeachersFeedState(
    val isLoading: Boolean = false,
    val list: List<TeacherFeedModel>? = null,
    val error: Boolean? = false
)