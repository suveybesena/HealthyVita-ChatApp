package com.suveybesena.schoolchattingapp.presentation.register.teacher


import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel

sealed class TeachersRegisterEvent {
    data class CreateUser(val registerModel: RegisterModel) : TeachersRegisterEvent()
}
