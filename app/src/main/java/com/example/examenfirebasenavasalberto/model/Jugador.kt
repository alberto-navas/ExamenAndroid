package com.example.examenfirebasenavasalberto.model

import com.google.firebase.firestore.DocumentId

data class Jugador(
    @DocumentId
    val id: String = "",
    val nombre: String = "",
    val dorsal: Int = 0,
    val posicion: String = "",
    val descripcion: String = "",
    val imageUrl: String = ""
)
