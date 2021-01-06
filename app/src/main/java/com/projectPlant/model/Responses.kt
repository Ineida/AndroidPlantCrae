package com.projectPlant.model

data class LoginResponse(
    val idPerson: Int?,
    val token: String,
    val username: String
)