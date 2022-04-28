package com.suveybesena.schoolchattingapp.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.auth.model.LoginModel
import com.suveybesena.schoolchattingapp.domain.LoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginUseCase: LoginUseCase) : ViewModel() {

    private val uiState = MutableStateFlow(LoginState())
    val _uiState: StateFlow<LoginState> = uiState.asStateFlow()

    fun handleEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.Login -> {
                event.loginInfo?.let { signInWithEmail(it) }
            }

        }
    }

    private fun signInWithEmail(loginInfo: LoginModel) {
        viewModelScope.launch {
            loginUseCase.invoke(loginInfo).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(loggedIn = true)
                        }
                    }
                    is Resource.Loading->{
                        uiState.update {state->
                            state.copy()

                        }
                    }
                }
            }
        }
    }


}