package com.example.tp_mobile.views.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ImageButton
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

                when {
                    mail.isBlank() -> {
                        inputMail.error = "Please enter your e-mail address"
                    }
                    !Regex(emailRegex).matches(mail) -> {
                        inputMail.error = "Please enter a valid e-mail address"
                    }
                    password.isBlank() -> {
                        inputPassword.error = "Please enter your password"
                    }
                    password.length < 6 -> {
                        inputPassword.error = "Your password must contain at least 6 characters"
                    }
                    password != confirmPassword -> {
                        inputConfirmPassword.error = "Passwords don't match"
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
                                    "Account created successfully !",
                                    Toast.LENGTH_SHORT
                                ).show()

                                val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
                                startActivity(intent)
                                finish()
                            }

                        } catch (e: FirebaseAuthUserCollisionException) {
                            if (e.errorCode == "ERROR_EMAIL_ALREADY_IN_USE") {
                                withContext(Dispatchers.Main) {
                                    inputMail.error = "The e-mail address is already in use."
                                }
                                return@launch
                            }

                        } catch (e: Exception) {
                            withContext(Dispatchers.Main) {
                                Toast.makeText(
                                    this@CreateAccountActivity,
                                    "Account creation error : ${e.message}",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                }
            }

        }
        binding.signUpButton.setOnClickListener{
            createAccountViewModel.signInWithGoogle(this)
        }



        val backButton: ImageButton = binding.back

        backButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

    }
}