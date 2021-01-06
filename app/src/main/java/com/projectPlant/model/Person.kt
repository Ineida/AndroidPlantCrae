package com.projectPlant.model

import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Keep
@Entity(tableName = "person")
data class Person(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id_person")
    private var idPerson: Int = 0,

    @ColumnInfo(name = "lastname")
    private var surname: String = "",

    @ColumnInfo(name = "firstname")
    private var name: String = "",

    @ColumnInfo(name = "birth_day")
    private var birthdate: Date,

    @ColumnInfo(name = "gender")
    private var gender: String = "",

    @ColumnInfo(name = "user")
    private var username: String = ""
)
