package com.suveybesena.schoolchattingapp.data.firebase.firestore.model

import android.net.Uri

data class Teacher(
    val mail: String,
    val profileImage: Uri,
    val password: String,
    val field: String,
    val userName : String
)
