package com.example.examenfirebasenavasalberto.model

import com.google.firebase.firestore.DocumentId

/**
 * Modelo de datos para los jugadores del Unicaja Baloncesto.
 * Firestore requiere un constructor vacío, por lo que asignamos valores por defecto.
 */
data class Jugador(
    @DocumentId
    val id: String = "",
    val nombre: String = "",
    val dorsal: Int = 0,
    val posicion: String = "",
    val nacionalidad: String = "",
    val imageUrl: String = ""
)
