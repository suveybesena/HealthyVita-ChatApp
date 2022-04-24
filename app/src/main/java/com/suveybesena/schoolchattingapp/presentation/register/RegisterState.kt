package com.suveybesena.schoolchattingapp.presentation.register

data class RegisterState(
    var error: String? = null,
    val isLoading: Boolean? = false,
    val isRegister: Boolean? = false
)
