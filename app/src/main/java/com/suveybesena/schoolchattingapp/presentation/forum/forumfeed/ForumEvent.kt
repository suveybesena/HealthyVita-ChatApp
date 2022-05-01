package com.suveybesena.schoolchattingapp.presentation.forum.forumfeed

import com.suveybesena.schoolchattingapp.data.firebase.firestore.model.ForumModel

sealed class ForumEvent {
    data class GetPatientData(val currentUserId: String?) : ForumEvent()
    data class GetDoctorData(val currentUserId: String?) : ForumEvent()
    object GetForumData : ForumEvent()
    data class AddForumDataToFirebase(val forumModel: ForumModel?) : ForumEvent()
}
