package com.example.examenfirebasenavasalberto.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed class Routes : NavKey {

    @Serializable
    data object Login : Routes()

    @Serializable
    data object Home : Routes()

    @Serializable
    data object NuevoJugador : Routes()

    @Serializable
    data class DetalleJugador(val id: String) : Routes()
}