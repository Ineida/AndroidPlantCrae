package com.projectPlant.service

import com.projectPlant.model.Person
import com.projectPlant.model.PersonRequest
import retrofit2.Call
import retrofit2.http.*

interface IApiPersonService {
    @POST("person/")
    fun addPersonalInformation(
        @Body person: PersonRequest,
        @Header("Authorization") barrier: String
    ): Call<Person>


    @POST("person/edit")
    fun editPersonalInformation(
        @Body person: PersonRequest,
        @Header("Authorization") barrier: String
    ): Call<Person>

    @GET("person/{id}")
    fun getPerson(@Path("id") id: Int, @Header("Authorization") barrier: String): Call<Person>

}