package com.suveybesena.schoolchattingapp.presentation.forum.forumfeed

import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumModel
import com.suveybesena.schoolchattingapp.presentation.forum.UserModel

data class ForumState(
    val doctorInfo: UserModel? = null,
    val patientInfo: UserModel? = null,
    val forumInfo: List<ForumModel>? = null,
    val isLoading: Boolean? = false,
    val error: String? = null
)
