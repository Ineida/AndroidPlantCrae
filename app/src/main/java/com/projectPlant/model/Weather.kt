package com.projectPlant.model

data class Weather(
    var day: String = "", var maxtemp_c: Double = 0.0, var mintemp_c: Double = 0.0,
    var avgtemp_c: Double = 0.0, var maxwind_mph: Double = 0.0, var maxwind_kp: Double = 0.0,
    var totalprecip_mm: Double = 0.0, var avgvis_km: Double = 0.0, var avghumidity: Double = 0.0,
    var conditionIcon: String = "", var conditionText: String = "",
    var conditionCode: Int = 0, var willRain: Boolean = false
)