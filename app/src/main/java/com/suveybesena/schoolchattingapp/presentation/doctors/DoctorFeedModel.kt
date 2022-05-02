package com.suveybesena.schoolchattingapp.presentation.doctors

import android.os.Parcel
import android.os.Parcelable

data class DoctorFeedModel(
    val id: String?,
    val name: String?,
    val image: String?,
    val field: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeString(field)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DoctorFeedModel> {
        override fun createFromParcel(parcel: Parcel): DoctorFeedModel {
            return DoctorFeedModel(parcel)
        }

        override fun newArray(size: Int): Array<DoctorFeedModel?> {
            return arrayOfNulls(size)
        }
    }
}
