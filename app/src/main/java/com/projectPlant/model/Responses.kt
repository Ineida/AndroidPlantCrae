package com.projectPlant.model

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class LoginResponse(
    val idPerson: Int?,
    val token: String,
    val username: String
)

data class PlantBody(
    val active: Boolean = true,
    val outSide: Boolean = false,
    val idPerson: Int,
    val idPlant: Int,
    val waterMin: String = "0.0",
    val waterMax: String = "0.0"
)


data class PlantPersonSimple(
    @Json(name = "idPPlant")
    var id: Int = 0,
    var idPerson: Int = 0,
    var idPlant: Int = 0,
    var active: Boolean = true,
    var outSide: Boolean = false,
    var waterMax: String = "0.0",
    var waterMin: String = "0.0"
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeInt(idPerson)
        parcel.writeInt(idPlant)
        parcel.writeByte(if (active) 1 else 0)
        parcel.writeByte(if (outSide) 1 else 0)
        parcel.writeString(waterMax)
        parcel.writeString(waterMin)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PlantPersonSimple> {
        override fun createFromParcel(parcel: Parcel): PlantPersonSimple {
            return PlantPersonSimple(parcel)
        }

        override fun newArray(size: Int): Array<PlantPersonSimple?> {
            return arrayOfNulls(size)
        }
    }

    fun getPlant(): Plant {
        var resultat: Plant = Plant(
            id = id, idperson = idPerson,
            idPlant = idPlant, active = active, outSide = outSide,
            waterMax = waterMax.toDouble(), waterMin = waterMin.toDouble()
        )
        return resultat
    }
}
