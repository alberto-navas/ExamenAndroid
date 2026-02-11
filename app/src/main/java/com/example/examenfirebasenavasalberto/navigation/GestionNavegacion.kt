package com.example.examenfirebasenavasalberto.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.example.examenfirebasenavasalberto.screens.DetalleJugadorScreen
import com.example.examenfirebasenavasalberto.screens.HomeScreen
import com.example.examenfirebasenavasalberto.screens.LoginScreen
import com.example.examenfirebasenavasalberto.screens.NuevoJugadorScreen
import com.example.examenfirebasenavasalberto.viewmodel.AuthViewModel
import com.example.examenfirebasenavasalberto.viewmodel.JugadorViewModel

@Composable
fun GestionNavegacion() {
    val authViewModel: AuthViewModel = viewModel()
    val jugadorViewModel: JugadorViewModel = viewModel()
    
    val startRoute = if (authViewModel.userEmail.value != null) Routes.Home else Routes.Login
    val backStack = rememberNavBackStack(startRoute)

    NavDisplay(
        backStack = backStack,
        onBack = {
            if (backStack.size > 1) {
                backStack.removeAt(backStack.size - 1)
            }
        },
        entryProvider = { key ->
            // Forzamos el cast a Routes para que el 'when' sea exhaustivo
            when (val route = key as Routes) {
                is Routes.Login -> NavEntry(route) {
                    LoginScreen(
                        authViewModel = authViewModel,
                        onLoginSuccess = {
                            backStack.clear()
                            backStack.add(Routes.Home)
                        }
                    )
                }

                is Routes.Home -> NavEntry(route) {
                    HomeScreen(
                        userEmail = authViewModel.userEmail.value ?: "Usuario",
                        onLogout = {
                            authViewModel.signOut {
                                backStack.clear()
                                backStack.add(Routes.Login)
                            }
                        },
                        jugadorViewModel = jugadorViewModel,
                        onNuevoJugador = { backStack.add(Routes.NuevoJugador) },
                        onVerJugador = { id -> backStack.add(Routes.DetalleJugador(id)) }
                    )
                }

                is Routes.NuevoJugador -> NavEntry(route) {
                    NuevoJugadorScreen(
                        jugadorViewModel = jugadorViewModel,
                        onNavigateBack = { backStack.removeAt(backStack.size - 1) }
                    )
                }

                is Routes.DetalleJugador -> NavEntry(route) {
                    val jugador = jugadorViewModel.jugadores.value.find { it.id == route.id }
                    if (jugador != null) {
                        DetalleJugadorScreen(
                            jugador = jugador,
                            onNavigateBack = { backStack.removeAt(backStack.size - 1) }
                        )
                    } else {
                        // Si el jugador no se encuentra, volvemos atrás como un efecto secundario
                        LaunchedEffect(Unit) {
                            if (backStack.size > 1) backStack.removeAt(backStack.size - 1)
                        }
                    }
                }
            }
        }
    )
}
