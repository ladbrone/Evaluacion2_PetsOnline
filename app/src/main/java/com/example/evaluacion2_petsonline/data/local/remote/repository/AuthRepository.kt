package com.example.evaluacion2_petsonline.repository

import android.content.Context
import com.example.evaluacion2_petsonline.data.local.SessionManager
import com.example.evaluacion2_petsonline.data.remote.ApiService
import com.example.evaluacion2_petsonline.data.remote.LoginRequest
import com.example.evaluacion2_petsonline.data.remote.RetrofitClient

class AuthRepository(context: Context) {
    private val api = RetrofitClient.create(context).create(ApiService::class.java)
    private val session = SessionManager(context)

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val response = api.login(LoginRequest(email, password))
            val token = response.authToken ?: return Result.failure(Exception("Token no recibido"))
            session.saveToken(token)
            Result.success(token)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
