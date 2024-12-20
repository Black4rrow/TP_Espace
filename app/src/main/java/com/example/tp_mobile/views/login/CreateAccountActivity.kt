package com.example.tp_mobile.views.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.R
import com.example.tp_mobile.databinding.ActivityCreateAccountBinding
import com.example.tp_mobile.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateAccountBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val createAccountViewModel: CreateAccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)

        binding.registerButton.setOnClickListener {

            val inputMail = binding.mail
            val inputPassword = binding.password
            val inputConfirmPassword = binding.confirmPassword
            val mail = inputMail.text.toString()
            val password = inputPassword.text.toString()
            val confirmPassword = inputConfirmPassword.text.toString()
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

            lifecycleScope.launch {

                // Vérification des erreurs de saisie
                when {
                    mail.isBlank() -> {
                        inputMail.error = "Veuillez svp renseigner votre adresse mail"
                    }
                    !Regex(emailRegex).matches(mail) -> {
                        inputMail.error = "Veuillez svp renseigner une adresse mail valide"
                    }
                    password.isBlank() -> {
                        inputPassword.error = "Veuillez svp renseigner votre mot de passe"
                    }
                    password.length < 6 -> {
                        inputPassword.error = "Votre mot de passe doit contenir au minimum 6 caractères"
                    }
                    password != confirmPassword -> {
                        inputConfirmPassword.error = "Les mots de passe ne correspondent pas"
                    }
                    else -> {

                        try {

                            createAccountViewModel.createAccount(
                                mail,
                                password,
                                sharedPreferences,
                                this@CreateAccountActivity
                            )

                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@CreateAccountActivity,
                                    "Compte créé avec succès",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                        } catch (e: FirebaseAuthUserCollisionException) {
                            if (e.errorCode == "ERROR_EMAIL_ALREADY_IN_USE") {
                                withContext(Dispatchers.Main) {
                                    inputMail.error = "L'adresse e-mail est déjà utilisée."
                                }
                                return@launch
                            }

                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@CreateAccountActivity,
                                    "Erreur lors de la création du compte : ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }

        }


    }
}