package com.suveybesena.schoolchattingapp.presentation.register.teacher

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.RegisterModel
import com.suveybesena.schoolchattingapp.domain.CreateTeacherUseCase
import com.suveybesena.schoolchattingapp.presentation.register.RegisterState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeacherRegisterViewModel @Inject constructor(
    private val createTeacherUseCase: CreateTeacherUseCase,
) :
    ViewModel() {

    private val uiState = MutableStateFlow(RegisterState())
    val _uiState: StateFlow<RegisterState> = uiState.asStateFlow()

    fun handleEvent(eventTeachers: TeachersRegisterEvent, field: String) {
        when (eventTeachers) {
            is TeachersRegisterEvent.CreateUser -> {
                createAuth(eventTeachers.registerModel,field)
            }
            else -> {}
        }
    }


    private fun createAuth(registerModel: RegisterModel, field : String) {
        viewModelScope.launch {
            createTeacherUseCase.invoke(registerModel, field).collect { resultState ->
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