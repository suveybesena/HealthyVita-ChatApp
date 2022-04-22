package com.suveybesena.schoolchattingapp.data.firebase.firestore.source


import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.suveybesena.schoolchattingapp.presentation.chat.MessageModel
import com.suveybesena.schoolchattingapp.presentation.forum.ForumModel
import kotlinx.coroutines.tasks.await
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList
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
            val time = System.currentTimeMillis()
            val teacherData = hashMapOf(
                "userEmail" to userMail,
                "userName" to userName,
                "userImage" to imageUrl,
                "userId" to currentUserId,
                "field" to field,
                "registerDate" to time
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

    override suspend fun addMessagesToFirebase(messages: MessageModel) {
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
                "imageUrl" to messages.imageUrl,
                "room" to list
            )
            firebaseFirestore.collection("Messages").document(uuid).set(message)
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
            return firebaseFirestore.collection("Messages").whereArrayContains("room", senderRoom)
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
                "forumMessages" to forum.forumMessage,
                "userId" to forum.userId,
                "time" to time,
                "messageId" to forumId
            )
            firebaseFirestore.collection("ForumMessages").document(forum.userId).set(forumMessages)

        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun fetchForumMessages(): List<DocumentSnapshot> {
        return try {
            firebaseFirestore.collection("ForumMessages").orderBy(
                "registerDate",
                Query.Direction.DESCENDING
            ).get().await().documents

        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

}