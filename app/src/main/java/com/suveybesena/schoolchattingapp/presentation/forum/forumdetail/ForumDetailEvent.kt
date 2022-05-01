package com.suveybesena.schoolchattingapp.presentation.forum.forumdetail

import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumDetailModel

sealed class ForumDetailEvent {
    data class AddForumMessage(val answers: ForumDetailModel) : ForumDetailEvent()
    data class FetchForumMessage(val messageId: String) : ForumDetailEvent()
}
