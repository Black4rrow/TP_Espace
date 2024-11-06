package com.example.tp_mobile.views.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var sharedPreferences: SharedPreferences

    private val loginViewModel: LoginViewModel by viewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)



        binding.loginButton.setOnClickListener(View.OnClickListener {
            val name = binding.username.text.toString()
            val password = binding.password.text.toString()

/*
            val isSuccess = loginViewModel.login(this, sharedPreferences, name,
                password.hashCode().toString()
            )
            if (isSuccess) {
                val intent = Intent(this, MainActivity::class.java)
                Log.e("shared", sharedPreferences.getString("name", null).toString())
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login Failed! try again", Toast.LENGTH_SHORT).show()
            }

 */


            loginViewModel.viewModelScope.launch {
                loginViewModel.login(
                    this@LoginActivity, sharedPreferences, name,
                    password.hashCode().toString()
                )

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@LoginActivity, "Vous êtes connecté", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(0,0)
                    finish()
                    overridePendingTransition(0,0)
                }
            }






        })

        binding.linkRegister.setOnClickListener(){
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
            overridePendingTransition(0,0)
        }


    }
}