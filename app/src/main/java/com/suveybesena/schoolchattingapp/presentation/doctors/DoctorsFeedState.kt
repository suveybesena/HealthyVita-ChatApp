package com.suveybesena.schoolchattingapp.presentation.doctors

data class DoctorsFeedState(
    val isLoading: Boolean = false,
    val list: List<DoctorFeedModel>? = null,
    val error: Boolean? = false
)