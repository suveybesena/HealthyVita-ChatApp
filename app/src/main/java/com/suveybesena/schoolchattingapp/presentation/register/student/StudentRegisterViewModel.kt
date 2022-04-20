package com.suveybesena.schoolchattingapp.presentation.register.student

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.domain.CreateStudentUseCase
import com.suveybesena.schoolchattingapp.presentation.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StudentRegisterViewModel @Inject constructor(private val createStudentUseCase: CreateStudentUseCase) :ViewModel(){

    private val uiState = MutableStateFlow(RegisterState())
    val _uiState: StateFlow<RegisterState> = uiState.asStateFlow()

    fun handleEvent(eventTeachers: StudentRegisterEvent) {
        when (eventTeachers) {
            is StudentRegisterEvent.CreateUser -> {
                createAuth(eventTeachers.registerModel)
            }
            else -> {}
        }
    }

    private fun createAuth(registerModel: RegisterModel) {
        viewModelScope.launch {
            createStudentUseCase.invoke(registerModel).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(isRegister = true)
                        }
                    }
                    is Resource.Error ->{
                        uiState.update { state->
                            state.copy(error = resultState.message)
                        }
                    }
                }
            }
        }
    }
}