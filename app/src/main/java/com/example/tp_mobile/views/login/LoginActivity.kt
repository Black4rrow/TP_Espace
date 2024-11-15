package com.example.tp_mobile.views.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bouton de connexion
        binding.loginButton.setOnClickListener {
            val inputMail = binding.mail
            val inputPassword = binding.password
            val mail = inputMail.text.toString()
            val password = inputPassword.text.toString()
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

            if (mail.isBlank()) {
                inputMail.error = "Veuillez renseigner votre adresse mail"
            } else if (!Regex(emailRegex).matches(mail)) {
                inputMail.error = "Veuillez renseigner une adresse mail valide"
            } else if (password.isBlank()) {
                inputPassword.error = "Veuillez renseigner votre mot de passe"
            } else if (password.length < 6) {
                inputPassword.error = "Votre mot de passe doit contenir au moins 6 caractères"
            } else {
                loginViewModel.viewModelScope.launch {
                    val loginSuccessful = loginViewModel.login(mail, password)
                    withContext(Dispatchers.Main) {
                        if (loginSuccessful) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Connexion réussie",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Échec de la connexion. Vérifiez vos identifiants.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }

        // Lien vers la création d’un compte
        binding.linkRegister.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
