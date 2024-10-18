package com.example.tp_mobile.views.login

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.tp_mobile.model.User
import com.example.tp_mobile.model.domain.UserRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.tasks.await


class LoginViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    private var users = mutableListOf<User>()
    fun login(
        context: Context,
        sharedPreferences: SharedPreferences,
        name: String,
        password: String
    ): Boolean {

        users = UserRepository.getUsersInSharedPreferences(sharedPreferences).toMutableList()

        users.forEach { user ->
            if (user.username == name && user.password.equals(password)) {
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }

    suspend fun signIn(email: String, password: String) {
        auth = Firebase.auth
        auth.signInWithEmailAndPassword(email, password).await()
    }


}
