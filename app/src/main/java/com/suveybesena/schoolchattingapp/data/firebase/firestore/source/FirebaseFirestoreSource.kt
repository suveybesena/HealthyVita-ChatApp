package com.suveybesena.schoolchattingapp.data.firebase.firestore.source


import com.google.firebase.firestore.DocumentSnapshot
import com.suveybesena.schoolchattingapp.presentation.chat.MessageModel
import com.suveybesena.schoolchattingapp.presentation.forum.ForumModel

interface FirebaseFirestoreSource {
    suspend fun addDoctorInfoToFirebase(
        userName: String,
        currentUserId: String,
        imageUrl: String,
        userMail: String,
        field: String
    )

    suspend fun addPatientInfoToFirebase(
        currentUserId: String, imageUrl: String, userName: String, userMail: String,
        userPassword: String
    )

    suspend fun fetchDoctorInfo(): List<DocumentSnapshot>

    suspend fun addMessagesToFirebase(messages: MessageModel)

    suspend fun fetchMessagesFromFirebase(
        currentUserId: String,
        receiverId: String
    ): List<DocumentSnapshot>

    suspend fun addForumToFirebase(forum: ForumModel)

    suspend fun fetchForumMessages(): List<DocumentSnapshot>

    suspend fun fetchCurrentPatientInfo(currentUserId: String): List<DocumentSnapshot>

    suspend fun fetchCurrentDoctorInfo(currentUserId: String): List<DocumentSnapshot>

}