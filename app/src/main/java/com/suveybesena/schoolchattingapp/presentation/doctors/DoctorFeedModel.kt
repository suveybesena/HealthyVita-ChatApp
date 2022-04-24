package com.suveybesena.schoolchattingapp.presentation.doctors

import java.io.Serializable

data class DoctorFeedModel(
    val id: String,
    val name: String,
    val image: String,
    val field: String
) : Serializable
