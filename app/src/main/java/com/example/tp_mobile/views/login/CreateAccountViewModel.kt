package com.example.tp_mobile.views.login

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.tp_mobile.model.User
import com.example.tp_mobile.model.domain.UserRepository

class CreateAccountViewModel : ViewModel() {
    fun createAccount(name : String , password : String,sharedPreferences: SharedPreferences,context: Context){
        var user = User(username = name, password = password)
        UserRepository.addUserToSharedPreferences(user,sharedPreferences)
        Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()

    }
}