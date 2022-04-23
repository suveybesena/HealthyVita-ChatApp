package com.suveybesena.schoolchattingapp.presentation.forum

sealed class ForumEvent {
    data class GetStudentData(val currentUserId : String?) : ForumEvent()
    object GetForumData : ForumEvent()
    data class AddForumDataToFirebase ( val forumModel: ForumModel?) : ForumEvent()
}
