package com.example.tp_mobile.views.login

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.R
import com.example.tp_mobile.databinding.ActivityLogoutRegisterBinding
import com.example.tp_mobile.views.APODNavigationControllerActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class LogoutRegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogoutRegisterBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogoutRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.buttonLogout.setOnClickListener {
            firebaseAuth.signOut()
            navigateToLoginScreen()
        }
        setUpNavBar()
    }
    private lateinit var bottomNavigationView: BottomNavigationView

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


        private fun navigateToLoginScreen() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
