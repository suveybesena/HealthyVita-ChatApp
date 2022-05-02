package com.suveybesena.schoolchattingapp.data.firebase.firestore.model

import android.os.Parcel
import android.os.Parcelable

data class ForumModel(
    val forumMessage: String?,
    val userId: String?,
    val time: Long,
    val messageId: String?,
    val userImage: String?,
    val userName: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(forumMessage)
        parcel.writeString(userId)
        parcel.writeLong(time)
        parcel.writeString(messageId)
        parcel.writeString(userImage)
        parcel.writeString(userName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ForumModel> {
        override fun createFromParcel(parcel: Parcel): ForumModel {
            return ForumModel(parcel)
        }

        override fun newArray(size: Int): Array<ForumModel?> {
            return arrayOfNulls(size)
        }
    }
}
