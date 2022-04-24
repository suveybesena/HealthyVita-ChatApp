package com.suveybesena.schoolchattingapp.presentation.doctors

sealed class DoctorsFeedEvent {
    object FetchDoctorsData : DoctorsFeedEvent()
}
