package com.suveybesena.schoolchattingapp.presentation.login

import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel

sealed class LoginEvent {
    data class Login(val loginInfo: LoginModel?) : LoginEvent()
}
