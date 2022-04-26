package com.suveybesena.schoolchattingapp.presentation.forum.forumfeed

import java.io.Serializable

data class ForumModel(
    val forumMessage: String,
    val userId: String,
    val time: Long,
    val messageId: String,
    val userImage: String,
    val userName: String

) : Serializable
