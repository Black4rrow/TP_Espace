package com.example.tp_mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp_mobile.databinding.ActivityMainBinding
import com.example.tp_mobile.views.APODNavigationControllerActivity
import com.example.tp_mobile.views.FireballNavigationControllerActivity
import com.example.tp_mobile.views.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.fireballButton.setOnClickListener {
            val intent = Intent(this, FireballNavigationControllerActivity::class.java)
            startActivity(intent)
        }

        setUpNavBar()

    }

    private fun setUpNavBar() {
        bottomNavigationView = findViewById(R.id.navBar)
        bottomNavigationView.setOnItemSelectedListener {
            try {
                when (it.itemId) {
                    R.id.home -> {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.apod -> {
                        val intent = Intent(this, APODNavigationControllerActivity::class.java)
                        startActivity(intent)
                        true
                    }

                    R.id.profile -> {
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
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