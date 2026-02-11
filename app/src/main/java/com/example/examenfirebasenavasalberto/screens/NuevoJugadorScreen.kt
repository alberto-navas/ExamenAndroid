package com.example.examenfirebasenavasalberto.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.examenfirebasenavasalberto.viewmodel.JugadorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NuevoJugadorScreen(
    jugadorViewModel: JugadorViewModel,
    onNavigateBack: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var dorsal by remember { mutableStateOf("") }
    var posicion by remember { mutableStateOf("") }
    var nacionalidad by remember { mutableStateOf("") }
    var imageUrl by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Añadir Nuevo Jugador") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre del jugador") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = dorsal, onValueChange = { dorsal = it }, label = { Text("Dorsal") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = posicion, onValueChange = { posicion = it }, label = { Text("Posición") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = nacionalidad, onValueChange = { nacionalidad = it }, label = { Text("Nacionalidad") }, modifier = Modifier.fillMaxWidth())
            OutlinedTextField(value = imageUrl, onValueChange = { imageUrl = it }, label = { Text("URL de la imagen") }, modifier = Modifier.fillMaxWidth())

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (nombre.isNotBlank() && dorsal.isNotBlank()) {
                        jugadorViewModel.agregarJugador(
                            nombre = nombre,
                            dorsal = dorsal.toIntOrNull() ?: 0,
                            posicion = posicion,
                            nacionalidad = nacionalidad,
                            imageUrl = imageUrl
                        )
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF27D21F),
                    contentColor = Color.White)
            ) {
                Text("Añadir Jugador")
            }
        }
    }
}