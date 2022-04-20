package com.suveybesena.schoolchattingapp.data.firebase.storage.source

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject

class FirebaseStorageSourceProvider @Inject constructor(private val firebaseStorage: FirebaseStorage) :
    FirebaseStorageSource {
    override suspend fun addImageToStorageForTeachers(profileImage: Uri, currentUserId: String): String {
        try {
            val reference = firebaseStorage.reference.child("teachersImages/${currentUserId}.jpg")
            reference.putFile(profileImage).await()
            return reference.downloadUrl.await().toString()
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }


    override suspend fun addImageToStorageForStudents(profileImage: Uri, currentUserId: String): String {
        try {
            val reference = firebaseStorage.reference.child("studentImages/${currentUserId}.jpg")
            reference.putFile(profileImage).await()
            return reference.downloadUrl.await().toString()
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }
}