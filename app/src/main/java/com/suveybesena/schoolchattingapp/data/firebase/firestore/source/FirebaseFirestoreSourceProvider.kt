package com.suveybesena.schoolchattingapp.data.firebase.firestore.source


import com.google.firebase.firestore.FirebaseFirestore
import java.lang.Exception
import javax.inject.Inject

class FirebaseFirestoreSourceProvider @Inject constructor(private val firebaseFirestore: FirebaseFirestore) :
    FirebaseFirestoreSource {
    override suspend fun addTeacherInfoToFirebase(currentUserId : String, imageUrl: String, userName:String, field : String) {
        try {
            val teacherData = hashMapOf(
                "userEmail" to userName,
                "userImage" to imageUrl,
                "userName" to currentUserId,
                "field" to field
            )
            firebaseFirestore.collection("Teacher").document(currentUserId).set(teacherData)
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun addStudentInfoToFirebase(currentUserId : String, imageUrl: String, userName:String) {
        try {
            val studentInfo = hashMapOf(
                "userEmail" to userName,
                "userImage" to imageUrl,
                "userName" to currentUserId
            )
            firebaseFirestore.collection("Student").document(currentUserId).set(studentInfo)
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }
}