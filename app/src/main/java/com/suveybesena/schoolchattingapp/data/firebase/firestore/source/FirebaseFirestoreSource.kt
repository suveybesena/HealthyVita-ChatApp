package com.suveybesena.schoolchattingapp.data.firebase.firestore.source

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.suveybesena.schoolchattingapp.presentation.chat.MessageModel
import com.suveybesena.schoolchattingapp.presentation.forum.ForumModel

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

    suspend fun addMessagesToFirebase(messages: MessageModel)

    suspend fun fetchMessagesFromFirebase(
        currentUserId: String,
        receiverId: String
    ): List<DocumentSnapshot>

    suspend fun addForumToFirebase(forum: ForumModel)

    suspend fun fetchForumMessages(): List<DocumentSnapshot>

    suspend fun fetchCurrentStudentInfo (currentUserId: String)  :List<DocumentSnapshot>


}