package com.example.tp_mobile.views.login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.tp_mobile.model.User
import com.example.tp_mobile.model.domain.UserRepository
import com.google.common.reflect.TypeToken
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.gson.Gson
import kotlinx.coroutines.tasks.await

class CreateAccountViewModel : ViewModel() {
    private lateinit var auth: FirebaseAuth


    suspend fun createAccount(
        mail: String,
        password: String,
        sharedPreferences: SharedPreferences,
        context: Context
    ) {
        var user = User(mail = mail, password = password)
        UserRepository.addUserToSharedPreferences(user, sharedPreferences)
        signUp(mail, password)
        Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()

    }

    private suspend fun signUp(mail: String, password: String) {
        auth = Firebase.auth
        auth.createUserWithEmailAndPassword(mail, password).await()
    }
    fun signInWithGoogle(context: Context) {
        Toast.makeText(context, "The code on the official documentation is deprecated !", Toast.LENGTH_SHORT).show()
    }





}