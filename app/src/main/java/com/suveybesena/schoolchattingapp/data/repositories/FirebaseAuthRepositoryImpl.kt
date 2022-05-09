package com.suveybesena.schoolchattingapp.data.repositories

import com.google.firebase.auth.FirebaseUser
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.domain.firebasesources.FirebaseAuthSource
import com.suveybesena.schoolchattingapp.domain.repositories.FirebaseAuthRepository
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(private val firebaseAuthSourceProvider: FirebaseAuthSource) :
    FirebaseAuthRepository {
    override suspend fun signUp(registerModel: RegisterModel): FirebaseUser? =
        firebaseAuthSourceProvider.signUpWithEmail(registerModel)

    override suspend fun getCurrentUserId(): String? {
        return firebaseAuthSourceProvider.getCurrentUserId()
    }

    override suspend fun signIn(signingInfo: LoginModel): FirebaseUser? {
        return firebaseAuthSourceProvider.signIn(signingInfo)
    }
}