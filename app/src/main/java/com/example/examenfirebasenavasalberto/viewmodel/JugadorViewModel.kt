package com.example.examenfirebasenavasalberto.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.examenfirebasenavasalberto.model.Jugador
import com.google.firebase.firestore.FirebaseFirestore

class JugadorViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val jugadoresCollection = db.collection("jugadores")

    private val _jugadores = mutableStateOf<List<Jugador>>(emptyList())
    val jugadores: State<List<Jugador>> = _jugadores

    init {
        escucharJugadores()
    }

    private fun escucharJugadores() {
        jugadoresCollection.addSnapshotListener { snapshot, error ->
            if (error == null && snapshot != null) {
                _jugadores.value = snapshot.toObjects(Jugador::class.java)
            }
        }
    }

    fun agregarJugador(nombre: String, dorsal: Int, posicion: String, nacionalidad: String, imageUrl: String) {
        val jugadorMap = hashMapOf(
            "nombre" to nombre,
            "dorsal" to dorsal,
            "posicion" to posicion,
            "nacionalidad" to nacionalidad,
            "imageUrl" to imageUrl
        )
        jugadoresCollection.add(jugadorMap)
    }

    fun eliminarJugador(id: String) {
        jugadoresCollection.document(id).delete()
    }
}
