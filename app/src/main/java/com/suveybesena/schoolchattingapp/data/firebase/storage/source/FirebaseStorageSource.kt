package com.suveybesena.schoolchattingapp.data.firebase.storage.source

import android.net.Uri

interface FirebaseStorageSource {
    suspend fun addImageToStorageForDoctors(profileImage: Uri, currentUserId: String): String
    suspend fun addImageToStorageForPatients(profileImage: Uri, currentUserId: String): String
    suspend fun addMessageImageToStorage(profileImage: Uri, currentUserId: String): String
}