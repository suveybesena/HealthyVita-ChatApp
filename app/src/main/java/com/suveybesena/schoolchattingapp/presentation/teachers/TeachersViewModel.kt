package com.suveybesena.schoolchattingapp.presentation.teachers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.domain.FetchTeacherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TeachersViewModel @Inject constructor(private val fetchTeacherInfoUseCase: FetchTeacherInfoUseCase): ViewModel() {

    private val uiState = MutableStateFlow(TeachersFeedState())
    val _uiState: StateFlow<TeachersFeedState> = uiState.asStateFlow()

    fun handleEvent(event: TeachersFeedEvent) {
        when(event){
            is TeachersFeedEvent.FetchTeachersData->{
                fetchTeacherData()
            }
        }
    }

    private fun fetchTeacherData() {
        viewModelScope.launch {
            fetchTeacherInfoUseCase.invoke().collect { resultState->
                when(resultState){
                    is Resource.Success->{
                        uiState.update { state->
                            state.copy(list = resultState.data as List<TeacherFeedModel>)
                        }
                    }
                }
            }
        }
    }
}