package com.suveybesena.schoolchattingapp.presentation.videocall

import android.os.Parcel
import android.os.Parcelable

data class VideoCallModel(
    val channelName: String?,
    val userRole: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readInt()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(channelName)
        parcel.writeInt(userRole)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<VideoCallModel> {
        override fun createFromParcel(parcel: Parcel): VideoCallModel {
            return VideoCallModel(parcel)
        }

        override fun newArray(size: Int): Array<VideoCallModel?> {
            return arrayOfNulls(size)
        }
    }
}
