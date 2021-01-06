package com.projectPlant.model

import android.os.Parcel
import android.os.Parcelable
import androidx.databinding.BaseObservable
import androidx.databinding.Bindable

data class User(
    private var _username: String = "",
    private var _password: String = ""
) : Parcelable, BaseObservable() {

    constructor(parcel: Parcel) : this(
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    var username: String
        @Bindable get() = _username
        set(value) {
            _username = value
            //notifyPropertyChanged(BR.login)
        }


    var password: String
        @Bindable get() = _password
        set(value) {
            _password = value
        }


    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(password)
    }


    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<User> {
        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {
            return arrayOfNulls(size)
        }
    }

}