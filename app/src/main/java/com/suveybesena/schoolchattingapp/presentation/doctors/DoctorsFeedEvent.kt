package com.suveybesena.schoolchattingapp.presentation.doctors

import com.suveybesena.schoolchattingapp.presentation.profile.ProfileEvent

sealed class DoctorsFeedEvent {
    object FetchDoctorsData : DoctorsFeedEvent()
    data class FetchDoctorData(val currentUserId: String?) : DoctorsFeedEvent()
    data class FetchPatientData(val currentUserId: String?) : DoctorsFeedEvent()
    data class FetchPatientMessages(val currentUserId: String?) : DoctorsFeedEvent()

}
