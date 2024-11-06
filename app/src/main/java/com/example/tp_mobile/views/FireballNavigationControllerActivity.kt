package com.example.tp_mobile.views

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.R
import com.example.tp_mobile.model.domain.api.FireballApiController
import com.example.tp_mobile.views.fireball.FireballListViewModel
import com.example.tp_mobile.views.fireball.FireballSectionFragment
import com.example.tp_mobile.views.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class FireballNavigationControllerActivity : AppCompatActivity() {

    private lateinit var viewModel: FireballListViewModel
    lateinit var backButton: ImageButton
    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fireball_navigation_controller)

        viewModel = ViewModelProvider(this)[FireballListViewModel::class.java]


        fragmentTransaction.replace(
            R.id.fireballFragmentContainer,
            FireballSectionFragment.newInstance()
        )
        fragmentTransaction.commit()

        setUpNavBar()
    }

    private fun setUpNavBar() {

        backButton = findViewById(R.id.back)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }


}