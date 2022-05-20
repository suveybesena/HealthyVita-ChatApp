package com.suveybesena.schoolchattingapp.domain.firebasesources

import android.net.Uri

interface FirebaseStorageRepository {
    suspend fun addImageToStorageForDoctors(profileImage: Uri, currentUserId: String): String
    suspend fun addImageToStorageForPatients(profileImage: Uri, currentUserId: String): String
    suspend fun addMessageImageToStorage(profileImage: Uri, currentUserId: String): String
}