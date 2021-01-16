package com.projectPlant.service

import com.projectPlant.model.Plant
import com.projectPlant.model.PlantPersonSimple
import com.projectPlant.model.PlantSimple
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.http.*

interface IApiPlantService {
    @POST("my-plants/")
    fun addMyPlant(
        @Body plant: PlantPersonSimple,
        @Header("Authorization") barrier: String
    ): Call<Boolean>

    @POST("my-plants/edit/{id}")
    fun editMyPlant(
        @Path("id") id: Int,
        @Body plant: PlantPersonSimple,
        @Header("Authorization") barrier: String
    ): Deferred<Boolean>

    @GET("my-plants/person/{id}")
    fun getPlants(
        @Path("id") id: Int?,
        @Header("Authorization") barrier: String
    ): Deferred<List<Plant>>

    @DELETE("my-plants/delete/{id}")
    fun deleteMyPlant(
        @Path("id") id: Int?,
        @Header("Authorization") barrier: String
    ): Deferred<Boolean>

    @DELETE("my-plants/sprinkle/{id}")
    fun waterMyPlant(
        @Path("id") id: Int?,
        @Query("quantity") quantity: Double,
        @Header("Authorization") barrier: String
    ): Deferred<Boolean>

    @POST("plants/")
    fun addPlant(@Body plant: PlantSimple, @Header("Authorization") barrier: String): Call<Plant>

    @GET("plants/")
    fun getAllPlants(
        @Query("q") q: String = " ",
        @Header("Authorization") barrier: String
    ): Call<List<PlantSimple>>

}