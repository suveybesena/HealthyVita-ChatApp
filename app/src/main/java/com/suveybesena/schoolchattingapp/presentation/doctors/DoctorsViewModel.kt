package com.suveybesena.schoolchattingapp.presentation.doctors

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.domain.FetchDoctorsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DoctorsViewModel @Inject constructor(private val fetchDoctorsUseCase: FetchDoctorsUseCase) :
    ViewModel() {

    private val uiState = MutableStateFlow(DoctorsFeedState())
    val _uiState: StateFlow<DoctorsFeedState> = uiState.asStateFlow()

    fun handleEvent(event: DoctorsFeedEvent) {
        when (event) {
            is DoctorsFeedEvent.FetchDoctorsData -> {
                fetchTeacherData()
            }
        }
    }

    private fun fetchTeacherData() {
        viewModelScope.launch {
            fetchDoctorsUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(list = resultState.data as List<DoctorFeedModel>)
                        }
                    }
                }
            }
        }
    }
}