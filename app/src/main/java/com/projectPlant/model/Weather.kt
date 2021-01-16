package com.projectPlant.model

data class Weather(
    var day: String = "",

    var maxtemp_c: String = "0.0",

    var mintemp_c: String = "0.0",

    var avgtemp_c: String = "0.0",

    var maxwind_mph: String = "0.0",

    var maxwind_kph: String = "0.0",

    var totalprecip_mm: String = "0.0",

    var avgvis_km: String = "0.0",

    var avghumidity: String = "0.0",

    var condition: WeatherInfo, var willRain: Boolean = false,

    var icon: String = "http:" + condition.icon

)

data class WeatherInfo(
    var code: Int = 0,

    var text: String = "",

    var icon: String = ""
)