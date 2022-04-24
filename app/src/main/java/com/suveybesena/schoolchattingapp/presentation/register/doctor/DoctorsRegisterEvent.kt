package com.suveybesena.schoolchattingapp.presentation.register.doctor


import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel

sealed class DoctorsRegisterEvent {
    data class CreateUser(val registerModel: RegisterModel) : DoctorsRegisterEvent()
}
