package com.example.tp_mobile.views.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewModelScope
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.R
import com.example.tp_mobile.databinding.ActivityLoginBinding
import com.example.tp_mobile.views.APODNavigationControllerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginButton.setOnClickListener {
            val inputMail = binding.mail
            val inputPassword = binding.password
            val mail = inputMail.text.toString()
            val password = inputPassword.text.toString()
            val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"

            if (mail.isBlank()) {
                inputMail.error = "Please enter your e-mail address"
            } else if (!Regex(emailRegex).matches(mail)) {
                inputMail.error = "Please enter a valid e-mail address"
            } else if (password.isBlank()) {
                inputPassword.error = "Please enter your password"
            } else if (password.length < 6) {
                inputPassword.error = "Your password must contain at least 6 characters"
            } else {
                //a changer
                loginViewModel.viewModelScope.launch {
                    val loginSuccessful = loginViewModel.login(mail, password)
                    withContext(Dispatchers.Main) {
                        if (loginSuccessful) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Successful connection",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(
                                this@LoginActivity,
                                "Connection failed. Please check your login details.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
        setUpNavBar()
        binding.linkRegister.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.signInGoogle.setOnClickListener(){
            loginViewModel.signInWithGoogle(this)
        }


    }
    private fun setUpNavBar() {
        bottomNavigationView = findViewById(R.id.navBar)
        bottomNavigationView.selectedItemId = R.id.profile

        bottomNavigationView.setOnItemSelectedListener {
            try {
                when (it.itemId) {

                    R.id.apod -> {
                        val intent = Intent(this, APODNavigationControllerActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        true
                    }

                    R.id.home -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0, 0)
                        true
                    }

                    else -> false
                }
            } catch (e: Exception) {
                throw e
            }
        }
    }

}
