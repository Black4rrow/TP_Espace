package com.example.tp_mobile.views.login

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await

class LoginViewModel : ViewModel() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun login(mail: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(mail, password).await()
            true
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Erreur de connexion : ${e.message}", e)
            false
        }
    }

    fun signInWithGoogle(context: Context) {
        Toast.makeText(context, "J'arrive pas, Mme Firebase est pas au top en ce moment", Toast.LENGTH_SHORT).show()    }
}
