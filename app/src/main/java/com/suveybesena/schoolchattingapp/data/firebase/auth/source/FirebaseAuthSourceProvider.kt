package com.suveybesena.schoolchattingapp.data.firebase.auth.source

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.userProfileChangeRequest
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthSourceProvider @Inject constructor(val firebaseAuth: FirebaseAuth) :
    FirebaseAuthSource {
    override suspend fun signUpWithEmail(emailAuthModel: RegisterModel): FirebaseUser? {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(
                emailAuthModel.email,
                emailAuthModel.password
            ).await()
            firebaseAuth.currentUser?.updateProfile(userProfileChangeRequest {
                displayName = emailAuthModel.userName
            })?.await()
            firebaseAuth.currentUser
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun getCurrentUserId(): String? {
        try {
            return firebaseAuth.currentUser?.uid
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }

    override suspend fun signIn(signInInfo: LoginModel): FirebaseUser? {
        return try {
            firebaseAuth.signInWithEmailAndPassword(signInInfo.email, signInInfo.password)
                .await()
            firebaseAuth.currentUser
        } catch (e: Exception) {
            throw Exception(e.localizedMessage)
        }
    }
}