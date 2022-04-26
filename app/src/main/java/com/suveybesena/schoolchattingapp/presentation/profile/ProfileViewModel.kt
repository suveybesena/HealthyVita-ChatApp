package com.suveybesena.schoolchattingapp.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.domain.FetchDoctorInfoUseCase
import com.suveybesena.schoolchattingapp.domain.FetchPatientInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val fetchTeachersUseCase: FetchDoctorInfoUseCase,
    private val fetchPatientInfoUseCase: FetchPatientInfoUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(ProfileState())
    val _uiState: StateFlow<ProfileState> = uiState.asStateFlow()


    fun handleEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.FetchDoctorData -> {
                event.currentUserId?.let { fetchTeacherData(it) }
            }
            is ProfileEvent.FetchPatientData -> {
                event.currentUserId?.let { fetchStudentData(it) }
            }
        }


    }

    private fun fetchStudentData(currentUserId: String) {
        viewModelScope.launch {
            fetchPatientInfoUseCase.invoke(currentUserId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(patientInfo = resultState.data)
                        }
                    }
                }
            }
        }
    }

    private fun fetchTeacherData(currentUserId: String) {
        viewModelScope.launch {
            fetchTeachersUseCase.invoke(currentUserId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(doctorInfo = resultState.data)
                        }
                    }
                }
            }
        }
    }

}