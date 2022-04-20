package com.suveybesena.schoolchattingapp.presentation.register.student

import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel


sealed class StudentRegisterEvent{
    data class CreateUser(val registerModel: RegisterModel) : StudentRegisterEvent()
}
