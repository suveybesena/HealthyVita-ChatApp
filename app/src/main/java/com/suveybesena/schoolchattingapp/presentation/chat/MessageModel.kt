package com.suveybesena.schoolchattingapp.presentation.chat

import java.sql.Timestamp


data class MessageModel(
    val message: String,
    var senderId: String,
    var imageUrl: String,
    var time: Long,
    val receiverId: String,
)
