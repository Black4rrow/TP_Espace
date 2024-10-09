package com.example.tp_mobile.views.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private lateinit var sharedPreferences: SharedPreferences

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE)

        val name = binding.username.text.toString()
        val password = binding.password.text.toString()

        /*
        val sharedEdit = sharedPreferences.edit()

        sharedEdit.putString("name", name)
        sharedEdit.putString("passWord", password)
        sharedEdit.apply()
        Toast.makeText(this, "Data (sharedPreferences) Successful!", Toast.LENGTH_SHORT).show()
        */

        binding.loginButton.setOnClickListener(View.OnClickListener {
            /*
            if (binding.username.text.toString() == "user" && binding.password.text.toString() == "1234"){
                Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                Log.e("shared", sharedPreferences.getString("name",null).toString())
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login Failed!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CreateAccountActivity::class.java)
                startActivity(intent)
                finish()
            }
            */
            val isSuccess = loginViewModel.login(this, sharedPreferences, name, password)
            if (isSuccess) {
                val intent = Intent(this, MainActivity::class.java)
                Log.e("shared", sharedPreferences.getString("name", null).toString())
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Login Failed! try again", Toast.LENGTH_SHORT).show()
            }

        })

        binding.linkRegister.setOnClickListener(){
            val intent = Intent(this, CreateAccountActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
}