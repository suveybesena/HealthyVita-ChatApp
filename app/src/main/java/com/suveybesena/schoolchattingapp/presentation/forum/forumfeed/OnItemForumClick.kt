package com.suveybesena.schoolchattingapp.presentation.forum.forumfeed

import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumModel

interface OnItemForumClick {
    fun onItemForumClick(forumModel: ForumModel)
}