package com.suveybesena.schoolchattingapp.presentation.forum.forumdetail

sealed class ForumDetailEvent{
    data class AddForumMessageUseCase(val answers: ForumDetailModel) : ForumDetailEvent()
    data class FetchForumMessageUseCase(val messageId : String) : ForumDetailEvent()
}
