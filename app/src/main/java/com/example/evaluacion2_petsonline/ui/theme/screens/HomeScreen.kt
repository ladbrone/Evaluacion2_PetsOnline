package com.example.evaluacion2_petsonline.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(text = "Pantalla Home", style = MaterialTheme.typography.headlineMedium)
            Button(onClick = { navController.navigate("profile") }) {
                Text("Ver Perfil")
            }
            Button(onClick = { navController.navigate("mascotas") }) {
                Text("Mis Mascotas")
            }
            Button(onClick = { navController.navigate("servicios") }) {
                Text("Servicios Veterinarios")
            }
            Button(onClick = { navController.navigate("productos") }) {
                Text("Productos")
            }
            Button(onClick = { navController.navigate("reserva") }) {
                Text("Reservar Cita")
            }
        }
    }
}
