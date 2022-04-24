package com.suveybesena.schoolchattingapp.presentation.chat


data class MessageModel(
    val message: String,
    var senderId: String,
    var imageUrl: String,
    var time: Long,
    val receiverId: String,
)
