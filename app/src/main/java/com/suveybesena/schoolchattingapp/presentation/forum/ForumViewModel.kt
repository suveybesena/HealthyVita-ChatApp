package com.suveybesena.schoolchattingapp.presentation.forum

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.domain.AddForumMessageUseCase
import com.suveybesena.schoolchattingapp.domain.FetchForumMessagesUseCase
import com.suveybesena.schoolchattingapp.domain.FetchStudentInfoUseCase
import com.suveybesena.schoolchattingapp.domain.FetchTeacherInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor(
    private val addForumMessageUseCase: AddForumMessageUseCase,
    private val fetchForumMessagesUseCase: FetchForumMessagesUseCase,
    val fetchTeacherInfoUseCase: FetchTeacherInfoUseCase,
    private val fetchStudentInfoUseCase: FetchStudentInfoUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(ForumState())
    val _uiState: StateFlow<ForumState> = uiState.asStateFlow()

    fun handleEvent(event: ForumEvent) {
        when (event) {
            is ForumEvent.GetStudentData -> {
                event.currentUserId?.let { getStudentData(it) }
            }
            is ForumEvent.AddForumDataToFirebase -> {
                viewModelScope.launch {
                    event.forumModel?.let { addForumMessageUseCase.invoke(it).collect { }
                    }
                }
            }
            is ForumEvent.GetForumData -> {
                getForumData()
            }
        }
    }

    private fun getForumData() {
        viewModelScope.launch {
            fetchForumMessagesUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(forumInfo = resultState.data as List<ForumModel>)
                        }
                    }
                    is Resource.Error->{
                        uiState.update { state->
                            state.copy(error = resultState.message)
                        }
                    }
                }

            }
        }
    }

    private fun getStudentData(currentUserId: String) {
        viewModelScope.launch {
            fetchStudentInfoUseCase.invoke(currentUserId).collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(currentStudentInfo = resultState.data)
                        }
                    }
                }

            }
        }

    }


}