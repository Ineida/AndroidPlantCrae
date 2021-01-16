package com.projectPlant.model

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import androidx.room.*

@Keep
@Entity(
    tableName = "my_plant", foreignKeys =
    [ForeignKey(
        entity = PlantSimple::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("id_simple_plant")
    )]
)
data class Plant(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @Ignore
    var personByIdperson: PersonInfo = PersonInfo(),

    @ColumnInfo(name = "idperson")
    var idperson: Int = personByIdperson.idperson,

    @Ignore
    var realHumidity: Double = 0.0,

    @Ignore
    var realTemperature: Double = 0.0,

    @ColumnInfo(name = "water_max")
    var waterMax: Double = 0.0,

    @ColumnInfo(name = "water_min")
    var waterMin: Double = 0.0,

    @ColumnInfo(name = "out_side")
    var outSide: Boolean = true,

    @ColumnInfo(name = "active")
    var active: Boolean = true,

    @Ignore
    var plantByIdplant: PlantSimple = PlantSimple(),

    @ColumnInfo(name = "id_simple_plant")
    var idPlant: Int = 0
)


@Keep
@Entity(tableName = "plant")
data class PlantSimple(
    @PrimaryKey
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "temperature_min")
    var temperatureMin: String = "0.0",

    @ColumnInfo(name = "temperature_max")
    var temperatureMax: String = "0.0",

    @ColumnInfo(name = "humidity_min")
    var humidityMin: String = "0.0",

    @ColumnInfo(name = "humidity_max")
    var humidityMax: String = "0.0",

    @ColumnInfo(name = "name")
    var name: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(temperatureMin)
        parcel.writeString(temperatureMax)
        parcel.writeString(humidityMin)
        parcel.writeString(humidityMax)
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "Name: $name, Temperature: $temperatureMin - $temperatureMax, Humidity: $humidityMin - $humidityMax"
    }

    companion object CREATOR : Parcelable.Creator<PlantSimple> {
        override fun createFromParcel(parcel: Parcel): PlantSimple {
            return PlantSimple(parcel)
        }

        override fun newArray(size: Int): Array<PlantSimple?> {
            return arrayOfNulls(size)
        }
    }
}