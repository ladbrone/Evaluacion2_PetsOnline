package com.example.evaluacion2_petsonline.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.evaluacion2_petsonline.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)

class LoginViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = AuthRepository(application)

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    // Expresión regular para validar correo electrónico
    private val emailRegex = "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$".toRegex()

    fun onEmailChange(value: String) {
        _uiState.value = _uiState.value.copy(email = value)
    }

    fun onPasswordChange(value: String) {
        _uiState.value = _uiState.value.copy(password = value)
    }

    fun login() {
        val state = _uiState.value

        // Validar si el correo tiene el formato correcto
        if (state.email.isBlank() || state.password.isBlank()) {
            _uiState.value = state.copy(error = "Todos los campos son obligatorios")
            return
        }

        if (!isValidEmail(state.email)) {
            _uiState.value = state.copy(error = "Correo electrónico inválido")
            return
        }

        viewModelScope.launch {
            _uiState.value = state.copy(isLoading = true, error = null)

            val result = repository.login(state.email, state.password)

            _uiState.value = result.fold(
                onSuccess = {
                    // Si el login es exitoso, guardar la sesión
                    _uiState.value.copy(isLoading = false, success = true)
                },
                onFailure = { error ->
                    // Si el login falla, mostrar el error adecuado
                    _uiState.value.copy(isLoading = false, error = error.message ?: "Error desconocido")
                }
            )
        }
    }

    // Función para validar el correo
    private fun isValidEmail(email: String): Boolean {
        return emailRegex.matches(email)
    }
}
