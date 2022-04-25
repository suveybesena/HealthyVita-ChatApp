package com.suveybesena.schoolchattingapp.presentation.chat

import android.net.Uri


data class MessageModel(
    val message: String,
    var senderId: String,
    var imageUrl: Uri?,
    var time: Long,
    val receiverId: String
)
