package com.projectPlant.model

data class PersonRequest(
    var name: String = "",
    var surname: String = "",
    var gender: String = "M",
    var birthdate: String = "",
    var username: String = "",
    var city: String = ""
)