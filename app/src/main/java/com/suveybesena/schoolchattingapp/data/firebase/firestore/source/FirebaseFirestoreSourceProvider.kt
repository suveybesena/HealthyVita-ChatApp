package com.suveybesena.schoolchattingapp.data.firebase.firestore.source


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumDetailModel
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumModel
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.MessageModel
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject

class FirebaseFirestoreSourceProvider @Inject constructor(private val firebaseFirestore: FirebaseFirestore) :
    FirebaseFirestoreSource {
    override suspend fun addDoctorInfoToFirebase(
        userName: String,
        currentUserId: String,
        imageUrl: String,
        userMail: String,
        field: String
    ) {
        try {
            val time = System.currentTimeMillis()
            val doctorData = hashMapOf(
                "userEmail" to userMail,
                "userName" to userName,
                "userImage" to imageUrl,
                "userId" to currentUserId,
                "field" to field,
                "registerDate" to time
            )
            firebaseFirestore.collection("Doctor").document(currentUserId).set(doctorData)
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun addPatientInfoToFirebase(
        currentUserId: String,
        imageUrl: String,
        userName: String,
        userMail: String,
        userPassword: String
    ) {
        try {
            val patientInfo = hashMapOf(
                "userName" to userName,
                "userImage" to imageUrl,
                "userId" to currentUserId,
                "userMail" to userMail,
                "password" to userPassword
            )
            firebaseFirestore.collection("Patient").document(currentUserId).set(patientInfo)
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun fetchDoctorInfo(): List<DocumentSnapshot> {
        try {
            return firebaseFirestore.collection("Doctor")
                .orderBy("registerDate", Query.Direction.DESCENDING).get().await().documents
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun addMessagesToFirebase(messages: MessageModel, imageUrl: String) {
        try {
            val uuid = UUID.randomUUID().toString()
            val senderRoom = messages.senderId + messages.receiverId
            val receiverRoom = messages.receiverId + messages.senderId
            val list = arrayListOf(senderRoom, receiverRoom)
            val message = hashMapOf(
                "sender" to messages.senderId,
                "receiver" to messages.receiverId,
                "time" to messages.time,
                "message" to messages.message,
                "imageUrl" to imageUrl,
                "room" to list
            )
            firebaseFirestore.collection("Messages").document(uuid).set(message).await()
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun fetchPatientMessageFromFirebase(
        currentUserId: String
    ): List<DocumentSnapshot> {
        try {
            return firebaseFirestore.collection("Messages").whereEqualTo("receiver", currentUserId)
                .get().await().documents
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun fetchMessagesFromFirebase(
        currentUserId: String,
        receiverId: String
    ): List<DocumentSnapshot> {
        try {
            val senderRoom = currentUserId + receiverId
            return firebaseFirestore.collection("Messages")
                .whereArrayContains("room", senderRoom).orderBy("time", Query.Direction.ASCENDING)
                .get().await().documents
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun addForumToFirebase(forum: ForumModel) {
        try {
            val forumId = UUID.randomUUID().toString()
            val time = System.currentTimeMillis()
            val forumMessages = hashMapOf(
                "forumMessage" to forum.forumMessage,
                "userId" to forum.userId,
                "time" to time,
                "messageId" to forumId,
                "userImage" to forum.userImage,
                "userName" to forum.userName
            )
            firebaseFirestore.collection("ForumMessages").document(forumId).set(forumMessages)
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun fetchForumMessages(): List<DocumentSnapshot> {
        try {
            return firebaseFirestore.collection("ForumMessages").orderBy(
                "time",
                Query.Direction.DESCENDING
            ).get().await().documents
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun fetchCurrentPatientInfo(currentUserId: String): List<DocumentSnapshot> {
        return try {
            firebaseFirestore.collection("Patient").whereEqualTo("userId", currentUserId).get()
                .await().documents
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun fetchCurrentDoctorInfo(currentUserId: String): List<DocumentSnapshot> {
        return try {
            firebaseFirestore.collection("Doctor").whereEqualTo("userId", currentUserId).get()
                .await().documents
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun addForumAnswers(forumAnswersModel: ForumDetailModel) {
        try {
            val forumId = UUID.randomUUID().toString()
            val time = System.currentTimeMillis()
            val message = hashMapOf(
                "messageId" to forumAnswersModel.messageId,
                "answers" to forumAnswersModel.message,
                "time" to time
            )
            firebaseFirestore.collection("ForumAnswers").document(forumId).set(message).await()
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun fetchForumAnswers(messageId: String): List<DocumentSnapshot> {
        return try {
            firebaseFirestore.collection("ForumAnswers").orderBy(
                "time",
                Query.Direction.DESCENDING).whereEqualTo("messageId", messageId)
                .get().await().documents
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }
}