package com.example.examenfirebasenavasalberto.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _userEmail = mutableStateOf<String?>(auth.currentUser?.email)
    val userEmail: State<String?> = _userEmail

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    fun signIn(email: String, password: String, onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            _error.value = "Email y contraseña requeridos"
            return
        }
        _isLoading.value = true
        _error.value = null
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _userEmail.value = auth.currentUser?.email
                    onSuccess()
                } else {
                    _error.value = task.exception?.localizedMessage
                }
            }
    }

    fun signUp(email: String, password: String, onSuccess: () -> Unit) {
        if (email.isBlank() || password.isBlank()) {
            _error.value = "Email y contraseña requeridos"
            return
        }
        _isLoading.value = true
        _error.value = null
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoading.value = false
                if (task.isSuccessful) {
                    _userEmail.value = auth.currentUser?.email
                    onSuccess()
                } else {
                    _error.value = task.exception?.localizedMessage
                }
            }
    }

    fun signOut(onSuccess: () -> Unit) {
        auth.signOut()
        _userEmail.value = null
        onSuccess()
    }

    fun clearError() {
        _error.value = null
    }
}
