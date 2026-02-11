package com.example.examenfirebasenavasalberto.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.examenfirebasenavasalberto.screens.*
import com.example.examenfirebasenavasalberto.viewmodel.AuthViewModel
import com.example.examenfirebasenavasalberto.viewmodel.JugadorViewModel

@Composable
fun GestionNavegacion() {
    // Instancia de los ViewModels siguiendo la arquitectura MVVM
    val authViewModel: AuthViewModel = viewModel()
    val jugadorViewModel: JugadorViewModel = viewModel()

    // Si ya hay un usuario logueado, empezamos en Home, si no en Login
    val startRoute = if (authViewModel.userEmail.value != null) Routes.Home else Routes.Login
    val backStack = rememberNavBackStack(startRoute)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Routes.Login -> NavEntry(key) {
                    LoginScreen(
                        authViewModel = authViewModel,
                        onLoginSuccess = {
                            while (backStack.isNotEmpty()) backStack.removeLastOrNull()
                            backStack.add(Routes.Home)
                        }
                    )
                }

                is Routes.Home -> NavEntry(key) {
                    HomeScreen(
                        userEmail = authViewModel.userEmail.value ?: "Usuario",
                        onLogout = {
                            authViewModel.signOut {
                                while (backStack.isNotEmpty()) backStack.removeLastOrNull()
                                backStack.add(Routes.Login)
                            }
                        },
                        jugadorViewModel = jugadorViewModel,
                        onNuevoJugador = { backStack.add(Routes.NuevoJugador) },
                        onVerJugador = { id -> backStack.add(Routes.DetalleJugador(id)) }
                    )
                }

                is Routes.NuevoJugador -> NavEntry(key) {
                    NuevoJugadorScreen(
                        jugadorViewModel = jugadorViewModel,
                        onNavigateBack = { backStack.removeLastOrNull() }
                    )
                }

                is Routes.DetalleJugador -> NavEntry(key) {
                    val jugador = jugadorViewModel.jugadores.value.find { it.id == key.id }
                    if (jugador != null) {
                        DetalleJugadorScreen(jugador = jugador, onNavigateBack = { backStack.removeLastOrNull() })
                    } else {
                        backStack.removeLastOrNull()
                    }
                }
            }
        }
    )
}
