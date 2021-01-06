package com.projectPlant.service

import com.projectPlant.model.LoginResponse
import com.projectPlant.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface IApiService {
    @POST("user/login")
    fun loginAsync(@Body user: User): Deferred<LoginResponse>

    @POST("user/inscription")
    fun createAccountAsync(@Body user: User): Deferred<LoginResponse>


    /* @POST("person/add")
     fun setPersonalInformation(@Body person: Person ) :Call<Boolean>

     @FormUrlEncoded
     @GET("person/")
     fun getPerson(@Field("id") id: String ) : Call<Person>

     */


}