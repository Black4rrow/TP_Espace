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

class CreateAccountViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth


    suspend fun createAccount(name : String, password : String, sharedPreferences: SharedPreferences, context: Context){
        var user = User(username = name, password = password)
        UserRepository.addUserToSharedPreferences(user,sharedPreferences)
        signUp(name,password)
        Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()


    }

    private suspend fun signUp(email: String, password: String) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(email, password).await()
    }
}