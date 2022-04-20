package com.suveybesena.schoolchattingapp.data.repository

import android.content.pm.SigningInfo
import android.net.Uri
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.data.firebase.auth.source.FirebaseAuthSourceProvider
import com.suveybesena.schoolchattingapp.data.firebase.firestore.source.FirebaseFirestoreSourceProvider
import com.suveybesena.schoolchattingapp.data.firebase.storage.source.FirebaseStorageSourceProvider
import javax.inject.Inject

class Repository @Inject constructor(
    private val firebaseAuthSourceProvider: FirebaseAuthSourceProvider,
    private val firebaseFirestoreSourceProvider: FirebaseFirestoreSourceProvider,
    private val firebaseStorageProvider: FirebaseStorageSourceProvider
) {
    suspend fun signUp(registerModel: RegisterModel) = firebaseAuthSourceProvider.signUpWithEmail(registerModel)

    suspend fun getCurrentUserId() = firebaseAuthSourceProvider.getCurrentUserId()

    suspend fun saveMediaToStorageForTeachers(profileImage: Uri, currentUserId: String) =
        firebaseStorageProvider.addImageToStorageForTeachers(profileImage, currentUserId)

    suspend fun saveMediaToStorageForStudents(profileImage: Uri, currentUserId: String) =
        firebaseStorageProvider.addImageToStorageForStudents(profileImage, currentUserId)


    suspend fun saveInfoToFirestoreForTeachers(
        currentUserId: String, imageUrl: String, userName: String, field : String
    ) =
        firebaseFirestoreSourceProvider.addTeacherInfoToFirebase(
            currentUserId, imageUrl, userName, field
        )

    suspend fun saveInfoToFirestoreForStudents(
        currentUserId: String, imageUrl: String, userName: String
    ) =
        firebaseFirestoreSourceProvider.addStudentInfoToFirebase(
            currentUserId, imageUrl, userName
        )

    suspend fun signIn(signingInfo: LoginModel) = firebaseAuthSourceProvider.signIn(signingInfo)
}
