package com.example.tp_mobile.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.tp_mobile.R
import com.example.tp_mobile.model.retrofit.FireballApiController
import com.example.tp_mobile.views.fireball.FireballListViewModel
import com.example.tp_mobile.views.fireball.FireballSectionFragment

class FireballNavigationControllerActivity : AppCompatActivity() {

    val fireballApiController = FireballApiController()
    private lateinit var viewModel: FireballListViewModel
    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.activity_fireball_navigation_controller)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[FireballListViewModel::class.java]


        findNavController(R.id.fireballHostFragment).navigate(
            FireballNavigationControllerActivityDirections.goToFireballSection()
        )

    }


}