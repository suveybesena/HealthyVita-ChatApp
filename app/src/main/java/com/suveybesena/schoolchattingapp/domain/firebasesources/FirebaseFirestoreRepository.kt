package com.suveybesena.schoolchattingapp.domain.firebasesources


import com.google.firebase.firestore.DocumentSnapshot
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.MessageModel
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumDetailModel
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumModel

interface FirebaseFirestoreRepository {
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

    suspend fun addMessagesToFirebase(messages: MessageModel, imageUrl: String)

    suspend fun fetchMessagesFromFirebase(
        currentUserId: String,
        receiverId: String
    ): List<DocumentSnapshot>

    suspend fun addForumToFirebase(forum: ForumModel)

    suspend fun fetchForumMessages(): List<DocumentSnapshot>

    suspend fun fetchCurrentPatientInfo(currentUserId: String): List<DocumentSnapshot>

    suspend fun fetchCurrentDoctorInfo(currentUserId: String): List<DocumentSnapshot>

    suspend fun fetchForumAnswers(messageId: String): List<DocumentSnapshot>

    suspend fun addForumAnswers(forumAnswersModel: ForumDetailModel)

    suspend fun fetchPatientMessageFromFirebase(
        currentUserId: String
    ): List<DocumentSnapshot>
}