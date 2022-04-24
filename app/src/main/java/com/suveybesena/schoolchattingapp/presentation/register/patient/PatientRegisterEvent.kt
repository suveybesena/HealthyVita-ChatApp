package com.suveybesena.schoolchattingapp.presentation.register.patient

import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel


sealed class PatientRegisterEvent {
    data class CreateUser(val userRegister: RegisterModel) : PatientRegisterEvent()
}
