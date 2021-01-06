package com.projectPlant.model

data class Plant(
    var id: Int = 0, var temperatureMin: Double = 0.0, var temperatureMax: Double = 0.0,
    var humidityMin: Double = 0.0, var humidityMax: Double = 0.0, var name: String = ""
    , var color: String = "green"
)