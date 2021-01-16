package com.projectPlant.service

import com.projectPlant.model.LoginResponse
import com.projectPlant.model.User
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface IApiUserService {
    @POST("user/login")
    fun loginAsync(@Body user: User): Deferred<LoginResponse>

    @POST("user/inscription")
    fun createAccountAsync(@Body user: User): Deferred<LoginResponse>
}