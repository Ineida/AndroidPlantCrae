package com.projectPlant.service

import com.projectPlant.model.Weather
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface IApiWeatherService {
    @GET("weather/{city}/{date}")
    fun getWeather(
        @Path("city") city: String?,
        @Path("date") date: String,
        @Header("Authorization") barrier: String
    ): Call<Weather>

    @GET("weather/{city}")
    fun getWeather(
        @Path("city") city: String?,
        @Header("Authorization") barrier: String
    ): Call<Weather>

}