package com.projectPlant.model

import android.os.Parcel
import android.os.Parcelable

data class Person(
    var idperson: Int = 0,
    var surname: String = "",
    var name: String = "",
    var birthdate: String = "",
    var gender: String = "F",
    var username: String = "",
    var city: String = "",
    var age: Int = 0
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idperson)
        parcel.writeString(surname)
        parcel.writeString(name)
        parcel.writeString(birthdate)
        parcel.writeString(gender)
        parcel.writeString(username)
        parcel.writeString(city)
        parcel.writeInt(age)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Person> {
        override fun createFromParcel(parcel: Parcel): Person {
            return Person(parcel)
        }

        override fun newArray(size: Int): Array<Person?> {
            return arrayOfNulls(size)
        }
    }

}


data class PersonInfo(
    var idperson: Int = 0,

    var city: String = ""
)