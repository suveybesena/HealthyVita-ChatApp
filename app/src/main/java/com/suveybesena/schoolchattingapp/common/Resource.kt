package com.suveybesena.schoolchattingapp.common

sealed class Resource<T> {
    data class Error<T>(var message: String) : Resource<T>()
    data class Success<T>(var data: T) : Resource<T>()
    object Loading : Resource<Nothing>()
}
