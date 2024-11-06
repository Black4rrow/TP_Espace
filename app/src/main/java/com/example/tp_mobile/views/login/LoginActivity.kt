package com.example.tp_mobile.views.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.credentials.GetCredentialRequest
import androidx.lifecycle.viewModelScope
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.R
import com.example.tp_mobile.databinding.ActivityLoginBinding
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
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



        binding.loginButton.setOnClickListener {
            val inputMail = binding.mail
            val inputPassword = binding.password
            val mail = inputMail.text.toString()
            val password = inputPassword.text.toString()
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"


            if (mail.isBlank()) {
                inputMail.error = "Veuillez svp renseigner votre adresse mail"
            } else if (!Regex(emailRegex).matches(mail)) {
                inputMail.error = "Veuillez svp renseigner une adresse mail valide"
            } else if (password.isBlank()) {
                inputPassword.error = "Veuillez svp renseigner votre mot de passe"
            } else if (password.length < 6) {
                inputPassword.error = "Votre mot de passe doit contenir au minimum 6 caractères"
            } else {


                loginViewModel.viewModelScope.launch {
                    if (loginViewModel.isEmailAlreadyUsed(mail, sharedPreferences)) {
                        loginViewModel.login(
                            this@LoginActivity, sharedPreferences, mail,
                            password.hashCode().toString()
                        )
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Vous êtes connecté", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }

        }


        binding.signInGoogle.setOnClickListener {
            loginViewModel.signInWithGoogle(this@LoginActivity)
        }
    }




}