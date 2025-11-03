package com.example.evaluacion2_petsonline.ui.screens

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.evaluacion2_petsonline.utils.ImagePickerDialog
import com.example.evaluacion2_petsonline.viewmodel.ProfileViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(navController: NavController, vm: ProfileViewModel = viewModel()) {
    val uiState by vm.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                Text("Perfil de Usuario", style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(16.dp))

                // 🔹 Selector de imagen
                ImagePickerDialog(onImagePicked = { uri: Uri? ->
                    uri?.let {
                        vm.saveAvatar(it.toString())
                        scope.launch { snackbarHostState.showSnackbar("Imagen actualizada ✅") }
                    }
                })

                Spacer(Modifier.height(16.dp))

                // 🔹 Carga diferida del avatar persistido
                val avatarUri = uiState.avatarUri
                if (!avatarUri.isNullOrEmpty()) {
                    AsyncImage(
                        model = Uri.parse(avatarUri),
                        contentDescription = "Foto de perfil",
                        modifier = Modifier
                            .size(150.dp)
                            .padding(8.dp)
                    )
                } else {
                    Text(
                        text = "Sin imagen de perfil 😅",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(24.dp))

                Text(text = "Usuario logueado: ${uiState.email ?: "Sin correo"}")

                Spacer(Modifier.height(24.dp))

                Button(onClick = { navController.navigate("home") }) {
                    Text("Volver al Home")
                }

                Spacer(Modifier.height(12.dp))

                Button(
                    onClick = {
                        vm.logout()
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
                ) {
                    Text("Cerrar sesión")
                }
            }
        }
    }
}

