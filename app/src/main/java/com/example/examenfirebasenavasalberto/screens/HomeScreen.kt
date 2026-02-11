package com.example.examenfirebasenavasalberto.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.examenfirebasenavasalberto.ui.components.JugadorCard
import com.example.examenfirebasenavasalberto.viewmodel.JugadorViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    userEmail: String,
    onLogout: () -> Unit,
    jugadorViewModel: JugadorViewModel,
    onNuevoJugador: () -> Unit,
    onVerJugador: (String) -> Unit
) {
    val jugadores by jugadorViewModel.jugadores

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Plantilla temporada 25/26") },
                actions = {
                    IconButton(onClick = onLogout) {
                        Icon(Icons.Default.Logout, contentDescription = "Cerrar sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onNuevoJugador) {
                Icon(Icons.Default.Add, contentDescription = "Añadir Jugador")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Bienvenido, $userEmail",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (jugadores.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No hay jugadores registrados")
                }
            } else {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(bottom = 80.dp) // Espacio para el FAB
                ) {
                    items(jugadores) { jugador ->
                        JugadorCard(
                            jugador = jugador,
                            onVerJugador = onVerJugador,
                            onEliminarJugador = { jugadorViewModel.eliminarJugador(jugador.id) }
                        )
                    }
                }
            }
        }
    }
}
