package com.suveybesena.schoolchattingapp.presentation.doctors

sealed class DoctorsFeedEvent {
    object FetchDoctorsListFromFirebase : DoctorsFeedEvent()
    data class FetchDoctorData(val currentUserId: String?) : DoctorsFeedEvent()
    data class FetchPatientData(val currentUserId: String?) : DoctorsFeedEvent()
    data class FetchPatientMessages(val currentUserId: String?) : DoctorsFeedEvent()
}
