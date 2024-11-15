package com.example.tp_mobile.views.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * Fonction pour gérer la connexion d'un utilisateur avec Firebase.
     */
    suspend fun login(mail: String, password: String): Boolean {
        return try {
            // Tentative de connexion avec Firebase
            auth.signInWithEmailAndPassword(mail, password).await()
            true
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Erreur de connexion : ${e.message}", e)
            false
        }
    }
}
