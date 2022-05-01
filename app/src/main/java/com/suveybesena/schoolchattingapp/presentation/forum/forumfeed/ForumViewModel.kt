package com.suveybesena.schoolchattingapp.presentation.forum.forumfeed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumModel
import com.suveybesena.schoolchattingapp.domain.AddForumMessageUseCase
import com.suveybesena.schoolchattingapp.domain.FetchDoctorInfoUseCase
import com.suveybesena.schoolchattingapp.domain.FetchForumMessagesUseCase
import com.suveybesena.schoolchattingapp.domain.FetchPatientInfoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumViewModel @Inject constructor(
    private val addForumMessageUseCase: AddForumMessageUseCase,
    private val fetchForumMessagesUseCase: FetchForumMessagesUseCase,
    private val fetchDoctorUseCase: FetchDoctorInfoUseCase,
    private val fetchPatientInfoUseCase: FetchPatientInfoUseCase
) : ViewModel() {

    private val uiState = MutableStateFlow(ForumState())
    val _uiState: StateFlow<ForumState> = uiState.asStateFlow()

    fun handleEvent(event: ForumEvent) {
        when (event) {
            is ForumEvent.GetPatientData -> {
                event.currentUserId?.let { getPatientData(it) }
            }
            is ForumEvent.AddForumDataToFirebase -> {
                viewModelScope.launch {
                    event.forumModel?.let {
                        addForumMessageUseCase.invoke(it).collect { }
                    }
                }
            }
            is ForumEvent.GetForumData -> {
                getForumData()
            }
            is ForumEvent.GetDoctorData -> {
                getDoctorData(event.currentUserId)
            }
        }
    }

    private fun getDoctorData(currentUserId: String?) {
        viewModelScope.launch {
            if (currentUserId != null) {
                fetchDoctorUseCase.invoke(currentUserId).collect { resultState ->
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

    private fun getForumData() {
        viewModelScope.launch {
            fetchForumMessagesUseCase.invoke().collect { resultState ->
                when (resultState) {
                    is Resource.Success -> {
                        uiState.update { state ->
                            state.copy(forumInfo = resultState.data as List<ForumModel>)
                        }
                    }
                    is Resource.Error -> {
                        uiState.update { state ->
                            state.copy(error = resultState.message)
                        }
                    }
                }
            }
        }
    }

    private fun getPatientData(currentUserId: String) {
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
}