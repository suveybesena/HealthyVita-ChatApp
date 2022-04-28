package com.suveybesena.schoolchattingapp.presentation.login

import com.google.firebase.auth.FirebaseUser

data class LoginState(
    val error: String? = null,
    val isLoading: Boolean? = false,
    val loggedIn: Boolean? = null
)
