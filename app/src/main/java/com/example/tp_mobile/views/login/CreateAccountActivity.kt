package com.example.tp_mobile.views.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewModelScope
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.R
import com.example.tp_mobile.databinding.ActivityCreateAccountBinding
import com.example.tp_mobile.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CreateAccountActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCreateAccountBinding
    private lateinit var sharedPreferences: SharedPreferences
    private val createAccountViewModel: CreateAccountViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)


        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)
        binding.registerButton.setOnClickListener {
            val name = binding.username.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()

            if (password == confirmPassword) {
                // Lancer la coroutine dans le ViewModel pour créer le compte
                createAccountViewModel.viewModelScope.launch {
                    createAccountViewModel.createAccount(
                        name,
                        password.hashCode().toString(),
                        sharedPreferences,
                        this@CreateAccountActivity
                    )

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@CreateAccountActivity, "Compte créé", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@CreateAccountActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }

    }


}