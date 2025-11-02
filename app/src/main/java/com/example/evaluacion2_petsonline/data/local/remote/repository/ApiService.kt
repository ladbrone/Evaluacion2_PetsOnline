package com.example.evaluacion2_petsonline.data.remote

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val authToken: String?, // depende del JSON de tu Xano (ajustamos si es diferente)
    val message: String?
)

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("auth/me")
    suspend fun getProfile(@Header("Authorization") token: String): Any
}
