package com.suveybesena.schoolchattingapp.presentation.profile

import com.suveybesena.schoolchattingapp.presentation.forum.UserModel

data class ProfileState(
    val doctorInfo: UserModel? = null,
    val patientInfo: UserModel? = null
)