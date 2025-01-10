package com.example.tp_mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tp_mobile.databinding.ActivityMainBinding
import com.example.tp_mobile.views.APODNavigationControllerActivity
import com.example.tp_mobile.views.FireballNavigationControllerActivity
import com.example.tp_mobile.views.login.LoginActivity
import com.example.tp_mobile.views.login.LogoutRegisterActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.fireballButton.setOnClickListener {
            val intent = Intent(this, FireballNavigationControllerActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
        }

        binding.versionTextView.text = BuildConfig.VERSION_NAME

        setUpNavBar()
    }

    private fun setUpNavBar() {
        bottomNavigationView = findViewById(R.id.navBar)
        bottomNavigationView.setOnItemSelectedListener {
            try {
                when (it.itemId) {

                    R.id.apod -> {
                        val intent = Intent(this, APODNavigationControllerActivity::class.java)
                        startActivity(intent)
                        overridePendingTransition(0,0)
                        true
                    }

                    R.id.profile -> {

                        val firebaseAuth = FirebaseAuth.getInstance()
                        val currentUser = firebaseAuth.currentUser

                        if (currentUser != null) {
                            val intent = Intent(this, LogoutRegisterActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(0, 0)
                            true
                        } else {
                            val intent = Intent(this, LoginActivity::class.java)
                            startActivity(intent)
                            overridePendingTransition(0, 0)
                            true
                        }

                    }

                    else -> false
                }
            } catch (e: Exception) {
                throw e
            }
        }

        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
            }
        })
    }

}