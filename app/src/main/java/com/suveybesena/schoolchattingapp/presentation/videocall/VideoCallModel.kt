package com.suveybesena.schoolchattingapp.presentation.videocall

import java.io.Serializable

data class VideoCallModel(
    val channelName: String,
    val userRole: Int
) : Serializable
