package com.suveybesena.schoolchattingapp.presentation.register.patient


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.domain.CreatePatientUseCase
import com.suveybesena.schoolchattingapp.presentation.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PatientRegisterViewModel @Inject constructor(private val createPatientUseCase: CreatePatientUseCase) :
    ViewModel() {

    private val uiState = MutableStateFlow(RegisterState())
    val _uiState: StateFlow<RegisterState> = uiState.asStateFlow()

    fun handleEvent(event: PatientRegisterEvent) {
        when (event) {
            is PatientRegisterEvent.CreateUser -> {
                createAuth(event.userRegister)
            }
        }
    }

    private fun createAuth(registerModel: RegisterModel) {
        viewModelScope.launch {
            createPatientUseCase.invoke(registerModel).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(isRegister = true)
                        }
                    }
                    is Resource.Error -> {
                        uiState.update { state ->
                            state.copy(error = resultState.message)
                        }
                    }
                    is Resource.Loading -> {
                        uiState.update { state ->
                            state.copy(isLoading = true)
                        }
                    }
                }
            }
        }
    }
}