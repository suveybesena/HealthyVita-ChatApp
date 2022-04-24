package com.suveybesena.schoolchattingapp.presentation.chat



data class FetchedMessageModel(
    val message: String,
    var senderId: String,
    var imageUrl: String?,
    var time: Long,
    val receiverId: String
)
