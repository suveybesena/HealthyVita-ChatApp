package com.suveybesena.schoolchattingapp.data.firebase.storage.source

import android.net.Uri

interface FirebaseStorageSource {
    suspend fun addImageToStorageForTeachers (profileImage : Uri, currentUserId : String) :String
    suspend fun  addImageToStorageForStudents (profileImage : Uri, currentUserId : String) :String
}