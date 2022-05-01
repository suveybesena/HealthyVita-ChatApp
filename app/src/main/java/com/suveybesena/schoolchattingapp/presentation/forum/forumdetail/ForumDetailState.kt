package com.suveybesena.schoolchattingapp.presentation.forum.forumdetail

import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumDetailModel

data class ForumDetailState(
    val answerList : List<ForumDetailModel>? = null
)
