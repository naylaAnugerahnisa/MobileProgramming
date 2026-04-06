package com.example.studentapp

import android.os.Parcel
import android.os.Parcelable

data class UserData(
    val namaLengkap: String,
    val nim: String,
    val programStudi: String,
    val jenisKelamin: String,
    val hobi: List<String>
) : Parcelable {

    constructor(parcel: Parcel) : this(
        namaLengkap = parcel.readString() ?: "",
        nim = parcel.readString() ?: "",
        programStudi = parcel.readString() ?: "",
        jenisKelamin = parcel.readString() ?: "",
        hobi = parcel.createStringArrayList() ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(namaLengkap)
        parcel.writeString(nim)
        parcel.writeString(programStudi)
        parcel.writeString(jenisKelamin)
        parcel.writeStringList(hobi)
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<UserData> {
        override fun createFromParcel(parcel: Parcel): UserData = UserData(parcel)
        override fun newArray(size: Int): Array<UserData?> = arrayOfNulls(size)
    }
}