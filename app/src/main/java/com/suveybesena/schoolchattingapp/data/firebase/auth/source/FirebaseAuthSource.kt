package com.suveybesena.schoolchattingapp.data.firebase.auth.source

import com.google.firebase.auth.FirebaseUser
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel

interface FirebaseAuthSource {
    suspend fun signUpWithEmail(emailAuthModel: RegisterModel): FirebaseUser?
    suspend fun getCurrentUserId(): String?
    suspend fun signIn(signInInfo: LoginModel): FirebaseUser?
}