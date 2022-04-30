package com.suveybesena.schoolchattingapp.presentation.forum.forumdetail

sealed class ForumDetailEvent{
    data class AddForumMessage(val answers: ForumDetailModel) : ForumDetailEvent()
    data class FetchForumMessage(val messageId : String) : ForumDetailEvent()
}
