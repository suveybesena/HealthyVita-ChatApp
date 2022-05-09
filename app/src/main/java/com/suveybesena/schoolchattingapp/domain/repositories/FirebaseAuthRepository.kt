package com.suveybesena.schoolchattingapp.domain.repositories

import com.google.firebase.auth.FirebaseUser
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel

interface FirebaseAuthRepository {
    suspend fun signUp(registerModel: RegisterModel): FirebaseUser?
    suspend fun getCurrentUserId(): String?
    suspend fun signIn(signingInfo: LoginModel): FirebaseUser?
}