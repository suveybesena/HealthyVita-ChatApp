package com.suveybesena.schoolchattingapp.data.repository

import android.content.pm.SigningInfo
import android.net.Uri
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.data.firebase.auth.source.FirebaseAuthSourceProvider
import com.suveybesena.schoolchattingapp.data.firebase.firestore.source.FirebaseFirestoreSourceProvider
import com.suveybesena.schoolchattingapp.data.firebase.storage.source.FirebaseStorageSourceProvider
import com.suveybesena.schoolchattingapp.presentation.chat.MessageModel
import com.suveybesena.schoolchattingapp.presentation.forum.ForumModel
import javax.inject.Inject

class Repository @Inject constructor(
    private val firebaseAuthSourceProvider: FirebaseAuthSourceProvider,
    private val firebaseFirestoreSourceProvider: FirebaseFirestoreSourceProvider,
    private val firebaseStorageProvider: FirebaseStorageSourceProvider
) {
    suspend fun signUp(registerModel: RegisterModel) =
        firebaseAuthSourceProvider.signUpWithEmail(registerModel)

    suspend fun getCurrentUserId() = firebaseAuthSourceProvider.getCurrentUserId()

    suspend fun saveMediaToStorageForTeachers(profileImage: Uri, currentUserId: String) =
        firebaseStorageProvider.addImageToStorageForTeachers(profileImage, currentUserId)

    suspend fun saveMediaToStorageForStudents(profileImage: Uri, currentUserId: String) =
        firebaseStorageProvider.addImageToStorageForStudents(profileImage, currentUserId)


    suspend fun saveInfoToFirestoreForTeachers(
        userName: String, currentUserId: String, imageUrl: String, userMail: String, field: String
    ) =
        firebaseFirestoreSourceProvider.addTeacherInfoToFirebase(
            userName,
            currentUserId, imageUrl, userMail, field
        )

    suspend fun saveInfoToFirestoreForStudents(
        currentUserId: String, imageUrl: String, userName: String
    ) =
        firebaseFirestoreSourceProvider.addStudentInfoToFirebase(
            currentUserId, imageUrl, userName
        )

    suspend fun signIn(signingInfo: LoginModel) = firebaseAuthSourceProvider.signIn(signingInfo)

    suspend fun fetchTeacherData() =
        firebaseFirestoreSourceProvider.fetchTeacherInfo()

    suspend fun fetchMessages(currentUserId: String, receiverId: String) =
        firebaseFirestoreSourceProvider.fetchMessagesFromFirebase(currentUserId, receiverId)

    suspend fun saveMessageToFirestore(messageModel: MessageModel) =
        firebaseFirestoreSourceProvider.addMessagesToFirebase(messageModel)

    suspend fun saveForumMessagesToFirebase(forumModel: ForumModel) {
        firebaseFirestoreSourceProvider.addForumToFirebase(forumModel)
    }

    suspend fun fetchForumMessageFromFirebase() =
        firebaseFirestoreSourceProvider.fetchForumMessages()
}
