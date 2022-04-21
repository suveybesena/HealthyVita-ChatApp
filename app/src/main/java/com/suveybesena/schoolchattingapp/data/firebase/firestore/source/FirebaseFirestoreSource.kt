package com.suveybesena.schoolchattingapp.data.firebase.firestore.source

import com.google.firebase.firestore.DocumentSnapshot
import com.suveybesena.schoolchattingapp.presentation.chat.MessageModel

interface FirebaseFirestoreSource {
    suspend fun addTeacherInfoToFirebase(
        userName: String,
        currentUserId: String,
        imageUrl: String,
        userMail: String,
        field: String
    )

    suspend fun addStudentInfoToFirebase(currentUserId: String, imageUrl: String, userName: String)

    suspend fun fetchTeacherInfo(): List<DocumentSnapshot>

    suspend fun addMessagesToFirebase(messages: MessageModel, currentUserId: String)

    suspend fun fetchMessagesFromFirebase(receiverId : String): List<DocumentSnapshot>
}