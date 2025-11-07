package com.example.evaluacion2_petsonline.data.remote

import com.google.gson.annotations.SerializedName
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    @SerializedName("authToken")
    val authToken: String?,
    @SerializedName("user")
    val user: UserResponse?
)

data class UserResponse(
    val id: Int?,
    val email: String?
)

interface ApiService {

    @POST("auth/signup")
    suspend fun signup(@Body request: LoginRequest): LoginResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): LoginResponse

    @GET("auth/me")
    suspend fun getProfile(@Header("Authorization") token: String): Map<String, Any>
}
