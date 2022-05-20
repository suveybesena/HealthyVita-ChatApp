package com.suveybesena.schoolchattingapp.domain.firebasesources

import com.google.firebase.auth.FirebaseUser
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel

interface FirebaseAuthRepository {
    suspend fun signUpWithEmail(emailAuthModel: RegisterModel): FirebaseUser?
    suspend fun getCurrentUserId(): String?
    suspend fun signIn(signInInfo: LoginModel): FirebaseUser?
}