package com.example.tp_mobile.views.login

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.tp_mobile.model.User
import com.example.tp_mobile.model.domain.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await


class LoginViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    private var users = mutableListOf<User>()
    suspend fun login(
        context: Context,
        sharedPreferences: SharedPreferences,
        name: String,
        password: String
    ): Boolean {
        val isConnected = NetworkUtils.isInternetAvailable(context)
        users = UserRepository.getUsersInSharedPreferences(sharedPreferences).toMutableList()
        if (isConnected) {
            users.forEach { user ->
                if (user.username == name && user.password.equals(password)) {
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                    return true
                }
            }
            return false
        }else{
            return signIn(name,password)
        }
    }

    private suspend fun signIn(email: String, password: String): Boolean {
        return try {
            auth.signInWithEmailAndPassword(email, password).await()
            true
        } catch (e: Exception) {
            false
        }
    }


}