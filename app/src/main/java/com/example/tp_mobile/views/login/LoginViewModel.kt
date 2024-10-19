package com.example.tp_mobile.views.login

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.tp_mobile.model.User
import com.example.tp_mobile.model.domain.UserRepository
import com.google.common.reflect.TypeToken
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await


class LoginViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth
    private var users = mutableListOf<User>()
    suspend fun login(
        context: Context,
        sharedPreferences: SharedPreferences,
        mail: String,
        password: String
    ): Boolean {
        val isConnected = NetworkUtils.isInternetAvailable(context)
        users = UserRepository.getUsersInSharedPreferences(sharedPreferences).toMutableList()
        if (isConnected) {
            users.forEach { user ->
                if (user.mail == mail && user.password.equals(password)) {
                    Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                    return true
                }
            }
            return false
        }else{
            return signIn(mail,password)
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


    fun isEmailAlreadyUsed(mail: String, sharedPreferences: SharedPreferences): Boolean {
        val usersJson = sharedPreferences.getString("users", null)

        if (usersJson.isNullOrEmpty()) {
            return true
        }

        val gson = Gson()
        val userListType = object : TypeToken<List<User>>() {}.type
        val users: List<User> = gson.fromJson(usersJson, userListType)

        return users.any { it.mail == mail }
    }


}