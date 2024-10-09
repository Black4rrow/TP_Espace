package com.example.tp_mobile.views.login
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.tp_mobile.model.User
import com.example.tp_mobile.model.domain.UserRepository


class LoginViewModel: ViewModel() {
    private var users = mutableListOf<User>()
    fun login(
        context: Context,
        sharedPreferences: SharedPreferences,
        name: String,
        password: String
    ): Boolean {

        users = UserRepository.getUsersInSharedPreferences(sharedPreferences).toMutableList()

        users.forEach { user ->
            if (user.username == name && user.password == password) {
                Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return false
    }




}
