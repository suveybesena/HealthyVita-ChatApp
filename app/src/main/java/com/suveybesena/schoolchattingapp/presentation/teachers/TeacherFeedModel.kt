package com.suveybesena.schoolchattingapp.presentation.teachers

import java.io.Serializable

data class TeacherFeedModel(
    val id : String,
    val name : String,
    val image : String,
    val field : String
) :Serializable
