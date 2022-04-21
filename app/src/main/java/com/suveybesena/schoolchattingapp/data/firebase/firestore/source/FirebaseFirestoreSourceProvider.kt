package com.suveybesena.schoolchattingapp.data.firebase.firestore.source


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.suveybesena.schoolchattingapp.presentation.chat.MessageModel
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import kotlin.random.Random

class FirebaseFirestoreSourceProvider @Inject constructor(private val firebaseFirestore: FirebaseFirestore) :
    FirebaseFirestoreSource {
    override suspend fun addTeacherInfoToFirebase(
        userName: String,
        currentUserId: String,
        imageUrl: String,
        userMail: String,
        field: String
    ) {
        try {
            val date = com.google.firebase.Timestamp.now()
            val teacherData = hashMapOf(
                "userEmail" to userMail,
                "userName" to userName,
                "userImage" to imageUrl,
                "userId" to currentUserId,
                "field" to field,
                "registerDate" to date
            )
            firebaseFirestore.collection("Teacher").document(currentUserId).set(teacherData)
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun addStudentInfoToFirebase(
        currentUserId: String,
        imageUrl: String,
        userName: String
    ) {
        try {
            val studentInfo = hashMapOf(
                "userName" to userName,
                "userImage" to imageUrl,
                "userId" to currentUserId
            )
            firebaseFirestore.collection("Student").document(currentUserId).set(studentInfo)
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun fetchTeacherInfo(): List<DocumentSnapshot> {
        try {
            return firebaseFirestore.collection("Teacher")
                .orderBy("registerDate", Query.Direction.DESCENDING).get().await().documents
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun addMessagesToFirebase(messages: MessageModel, currentUserId: String) {
        try {
            val randomUUID = UUID.randomUUID().toString()
            val message = hashMapOf(
                "sender" to messages.senderId,
                "receiver" to messages.receiverId,
                "time" to messages.timestamp,
                "message" to messages.message,
                "imageUrl" to messages.imageUrl
            )
            firebaseFirestore.collection("Messages").document(randomUUID).set(message)
        } catch (e: Exception) {
        }
    }

    override suspend fun fetchMessagesFromFirebase(receiverId: String): List<DocumentSnapshot> {
        try {
            return firebaseFirestore.collection("Messages").whereEqualTo("receiver", receiverId)
                .orderBy("time").get().await().documents
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }
}