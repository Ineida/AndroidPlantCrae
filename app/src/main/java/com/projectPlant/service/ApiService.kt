package com.projectPlant.service

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

private const val BASE_URL = "http://192.168.56.1:8083/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


object ApiService {
    val retrofitPersonService: IApiPersonService by lazy { retrofit.create(IApiPersonService::class.java) }
    val retrofitPlantService: IApiPlantService by lazy { retrofit.create(IApiPlantService::class.java) }
    val retrofitUserService: IApiUserService by lazy { retrofit.create(IApiUserService::class.java) }
    val retrofitWeatherService: IApiWeatherService by lazy { retrofit.create(IApiWeatherService::class.java) }
}


