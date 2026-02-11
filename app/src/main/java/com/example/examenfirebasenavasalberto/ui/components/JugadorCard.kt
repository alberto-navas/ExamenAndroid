package com.example.examenfirebasenavasalberto.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.example.examenfirebasenavasalberto.model.Jugador

@Composable
fun JugadorCard(
    jugador: Jugador,
    onVerJugador: (String) -> Unit,
    onEliminarJugador: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onVerJugador(jugador.id) },
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF2FCEE)),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            AsyncImage(
                model = jugador.imageUrl,
                contentDescription = "Imagen de ${jugador.nombre}",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp),
                contentScale = ContentScale.Crop
            )
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(text = jugador.nombre, style = MaterialTheme.typography.titleLarge)
                    Text(text = "Dorsal: ${jugador.dorsal} | Posición: ${jugador.posicion}")
                    Text(text = "Nacionalidad: ${jugador.nacionalidad}", style = MaterialTheme.typography.bodySmall)
                }
                IconButton(onClick = onEliminarJugador) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Eliminar",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
