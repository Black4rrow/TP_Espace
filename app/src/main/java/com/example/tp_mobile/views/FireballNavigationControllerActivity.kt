package com.example.tp_mobile.views.fireball

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tp_mobile.R
import com.example.tp_mobile.model.retrofit.FireballApiController
import com.example.tp_mobile.viewmodel.FireballListViewModel

class FireballNavigationController : AppCompatActivity() {

    val fireballApiController = FireballApiController()
    private lateinit var viewModel: FireballListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContentView(R.layout.fragment_fireball_navigation_controller)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[FireballListViewModel::class.java]
        viewModel.fetchFireballData()

    }

}