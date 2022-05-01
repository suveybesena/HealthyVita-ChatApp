package com.suveybesena.schoolchattingapp.presentation.forum.forumdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.suveybesena.schoolchattingapp.common.Resource
import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumDetailModel
import com.suveybesena.schoolchattingapp.domain.AddForumAnswerUseCase
import com.suveybesena.schoolchattingapp.domain.FetchForumAnswersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForumDetailViewModel @Inject constructor(
    private val addForumAnswerUseCase: AddForumAnswerUseCase,
    private val fetchForumAnswersUseCase: FetchForumAnswersUseCase) : ViewModel() {

    private val uiState = MutableStateFlow(ForumDetailState())
    val _uiState: StateFlow<ForumDetailState> = uiState.asStateFlow()

    fun handleEvent ( event : ForumDetailEvent){
        when(event){
            is ForumDetailEvent.AddForumMessage->{
                addForumAnswerToFirebase(event.answers)
            }
            is ForumDetailEvent.FetchForumMessage->{
                fetchForumAnswers(event.messageId)
            }
        }
    }

    private fun fetchForumAnswers(messageId : String) {
        viewModelScope.launch {
            fetchForumAnswersUseCase.invoke(messageId).collect{resultState->
                when(resultState){
                    is Resource.Success->{
                        uiState.update { state->
                            state.copy( answerList = resultState.data as List<ForumDetailModel>)
                        }
                    }
                }
            }

            }
        }

    private fun addForumAnswerToFirebase(answersModel: ForumDetailModel) {
        viewModelScope.launch {
            addForumAnswerUseCase.invoke(answersModel).collect{}
        }
    }
}