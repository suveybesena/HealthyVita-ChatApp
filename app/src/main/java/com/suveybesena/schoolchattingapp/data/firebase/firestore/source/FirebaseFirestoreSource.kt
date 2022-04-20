package com.suveybesena.schoolchattingapp.data.firebase.firestore.source

import android.net.Uri
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.Teacher

interface FirebaseFirestoreSource {
    suspend fun addTeacherInfoToFirebase(currentUserId : String, imageUrl : String, userName : String, field : String)

    suspend fun addStudentInfoToFirebase(currentUserId : String, imageUrl : String, userName : String)
}