package com.example.evaluacion2_petsonline.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.evaluacion2_petsonline.viewmodel.SignupViewModel
import kotlinx.coroutines.launch

@Composable
fun SignupScreen(navController: NavController, vm: SignupViewModel = viewModel()) {
    val ui by vm.ui.collectAsState()
    val scope = rememberCoroutineScope()
    var localError by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(ui.success) {
        if (ui.success) {
            navController.navigate("login") {
                popUpTo("signup") { inclusive = true }
            }
        }
    }

    Scaffold { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text("Crear cuenta", style = MaterialTheme.typography.headlineMedium)

                OutlinedTextField(
                    value = ui.email,
                    onValueChange = {
                        vm.onEmail(it)
                        localError = null
                    },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    isError = localError?.contains("correo", ignoreCase = true) == true
                )

                OutlinedTextField(
                    value = ui.password,
                    onValueChange = {
                        vm.onPassword(it)
                        localError = null
                    },
                    label = { Text("Contraseña (mín. 8 caracteres)") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = localError?.contains("contraseña", ignoreCase = true) == true
                )

                OutlinedTextField(
                    value = ui.confirmPassword,
                    onValueChange = {
                        vm.onConfirmPassword(it)
                        localError = null
                    },
                    label = { Text("Confirmar contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    isError = localError?.contains("coinciden", ignoreCase = true) == true
                )

                val errorText = localError ?: ui.error
                if (!errorText.isNullOrEmpty()) {
                    Text(errorText, color = MaterialTheme.colorScheme.error)
                }

                Button(
                    onClick = {
                        scope.launch {
                            localError = validateFields(
                                email = ui.email,
                                password = ui.password,
                                confirmPassword = ui.confirmPassword
                            )

                            if (localError == null) {
                                vm.signup()
                            }
                        }
                    },
                    enabled = !ui.isLoading,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    if (ui.isLoading) CircularProgressIndicator(strokeWidth = 2.dp)
                    else Text("Registrarme")
                }

                TextButton(onClick = { navController.navigate("login") }) {
                    Text("¿Ya tienes cuenta? Inicia sesión")
                }
            }
        }
    }
}

private fun validateFields(email: String, password: String, confirmPassword: String): String? {
    if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
        return "Por favor completa todos los campos."
    }
    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
        return "Correo electrónico inválido."
    }
    if (password.length < 8) {
        return "La contraseña debe tener al menos 8 caracteres."
    }
    if (password != confirmPassword) {
        return "Las contraseñas no coinciden."
    }
    return null
}
