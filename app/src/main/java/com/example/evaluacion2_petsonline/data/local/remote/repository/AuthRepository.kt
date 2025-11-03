package com.example.evaluacion2_petsonline.repository

import android.content.Context
import com.example.evaluacion2_petsonline.data.local.SessionManager
import com.example.evaluacion2_petsonline.data.remote.ApiService
import com.example.evaluacion2_petsonline.data.remote.LoginRequest
import com.example.evaluacion2_petsonline.data.remote.RetrofitClient
import java.net.UnknownHostException
import java.net.SocketTimeoutException
import retrofit2.HttpException

class AuthRepository(context: Context) {
    private val api = RetrofitClient.create(context).create(ApiService::class.java)
    private val session = SessionManager(context)

    suspend fun saveToken(token: String) {
        session.saveToken(token)
    }

    suspend fun getToken(): String? {
        return session.getToken()
    }

    suspend fun login(email: String, password: String): Result<String> {
        return try {
            val response = api.login(LoginRequest(email, password))
            val token = response.authToken ?: return Result.failure(Exception("No se recibió token"))
            saveToken(token)
            Result.success(token)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 403) {
                        Result.failure(Exception("Correo o contraseña incorrectos"))
                    } else {
                        Result.failure(Exception("Error desconocido: ${e.message}"))
                    }
                }
                is UnknownHostException -> {
                    Result.failure(Exception("No se pudo conectar al servidor. Verifica tu conexión a Internet"))
                }
                is SocketTimeoutException -> {
                    Result.failure(Exception("La conexión ha tardado demasiado. Intenta de nuevo"))
                }
                else -> {
                    Result.failure(Exception("Ocurrió un error inesperado. Intenta más tarde"))
                }
            }
        }
    }

    suspend fun signup(email: String, password: String): Result<String> {
        return try {
            val response = api.signup(LoginRequest(email, password))
            val token = response.authToken ?: return Result.failure(Exception("No se recibió token"))
            saveToken(token)
            Result.success(token)
        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    if (e.code() == 400) {
                        Result.failure(Exception("Error en el registro: ${e.message}"))
                    } else {
                        Result.failure(Exception("Error desconocido: ${e.message}"))
                    }
                }
                is UnknownHostException -> {
                    Result.failure(Exception("No se pudo conectar al servidor. Verifica tu conexión a Internet"))
                }
                is SocketTimeoutException -> {
                    Result.failure(Exception("La conexión ha tardado demasiado. Intenta de nuevo"))
                }
                else -> {
                    Result.failure(Exception("Ocurrió un error inesperado. Intenta más tarde"))
                }
            }
        }
    }

    suspend fun getProfile(): Result<String> {
        return try {
            val token = session.getToken() ?: return Result.failure(Exception("No hay token guardado"))
            val profile = api.getProfile("Bearer $token")

            val email = profile["email"]?.toString() ?: "Sin correo"
            Result.success(email)

        } catch (e: Exception) {
            when (e) {
                is HttpException -> {
                    Result.failure(Exception("Error al obtener el perfil: ${e.message}"))
                }
                is UnknownHostException -> {
                    Result.failure(Exception("No se pudo conectar al servidor. Verifica tu conexión a Internet"))
                }
                is SocketTimeoutException -> {
                    Result.failure(Exception("La conexión ha tardado demasiado. Intenta de nuevo"))
                }
                else -> {
                    Result.failure(Exception("Ocurrió un error inesperado al obtener el perfil. Intenta más tarde"))
                }
            }
        }
    }
}
