package com.suveybesena.schoolchattingapp.presentation.forum

data class ForumState(
    val doctorInfo: UserModel? = null,
    val patientInfo: UserModel? = null,
    val forumInfo: List<ForumModel>? = null,
    val isLoading: Boolean? = false,
    val error: String? = null
)
