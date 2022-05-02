package com.suveybesena.schoolchattingapp.presentation.doctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.domain.FetchDoctorInfoUseCase
import com.suveybesena.schoolchattingapp.domain.FetchDoctorsUseCase
import com.suveybesena.schoolchattingapp.domain.FetchMessageFromPatientUseCase
import com.suveybesena.schoolchattingapp.domain.FetchPatientInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorsViewModel @Inject constructor(
    private val fetchDoctorsUseCase: FetchDoctorsUseCase,
    private val fetchDoctorInfoUseCase: FetchDoctorInfoUseCase,
    private val fetchPatientInfoUseCase: FetchPatientInfoUseCase,
    private val fetchPatientMessageUseCase: FetchMessageFromPatientUseCase
) :
    ViewModel() {

    private val uiState = MutableStateFlow(DoctorsFeedState())
    val _uiState: StateFlow<DoctorsFeedState> = uiState.asStateFlow()

    fun handleEvent(event: DoctorsFeedEvent) {
        when (event) {
            is DoctorsFeedEvent.FetchDoctorsListFromFirebase -> {
                fetchDoctorsListFromFirebase()
            }
            is DoctorsFeedEvent.FetchDoctorData -> {
                event.currentUserId?.let { fetchDoctorData(it) }
            }
            is DoctorsFeedEvent.FetchPatientData -> {
                event.currentUserId?.let { fetchPatientData(it) }
            }
            is DoctorsFeedEvent.FetchPatientMessages -> {
                event.currentUserId?.let { fetchPatientMessage(it) }
            }
        }
    }

    private fun fetchPatientMessage(currentUserId: String) {
        viewModelScope.launch {
            fetchPatientMessageUseCase.invoke(currentUserId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(patientMessage = resultState.data)
                        }
                    }
                    is Resource.Loading->{
                        uiState.update { state->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }

    private fun fetchPatientData(currentUserId: String) {
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

    private fun fetchDoctorData(currentUserId: String) {
        viewModelScope.launch {
            fetchDoctorInfoUseCase.invoke(currentUserId).collect { resultState ->
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

    private fun fetchDoctorsListFromFirebase() {
        viewModelScope.launch {
            fetchDoctorsUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(list = resultState.data as List<DoctorFeedModel>)
                        }
                    }
                    is Resource.Loading->{
                        uiState.update { state->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }
}