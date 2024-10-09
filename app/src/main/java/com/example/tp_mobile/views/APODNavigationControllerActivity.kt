package com.example.tp_mobile.views

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import coil.load
import com.example.tp_mobile.MainActivity
import com.example.tp_mobile.R
import com.example.tp_mobile.databinding.ActivityApodnavigationControllerBinding
import com.example.tp_mobile.databinding.ActivityMainBinding
import com.example.tp_mobile.model.Apod
import com.example.tp_mobile.views.apod.ApodViewModel
import com.example.tp_mobile.views.login.LoginActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class APODNavigationControllerActivity : AppCompatActivity() {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var backButton: ImageButton
    private lateinit var viewModel: ApodViewModel
    private lateinit var binding: ActivityApodnavigationControllerBinding
    var apod: Apod? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_apodnavigation_controller)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this)[ApodViewModel::class.java]

        setUpNavBar()
        setUpObservers()
        viewModel.fetchApod()
    }

    private fun setUpObservers(){
        apod = viewModel.apod.value

        viewModel.apod.observe(this) {
            updateView()
        }
    }

    private fun updateView() {
        val apod = viewModel.apod.value


        val imageView : ImageView = findViewById(R.id.apod_image)
        imageView.load(apod?.url)

        val title : TextView = findViewById(R.id.image_title)
        title.text = apod?.title

        val explanation : TextView = findViewById(R.id.apod_explanation)
        explanation.text = apod?.explanation

    }

    private fun setUpNavBar() {
        bottomNavigationView = findViewById(R.id.navBar)
        bottomNavigationView.selectedItemId = R.id.apod

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

        backButton = findViewById(R.id.back)
        backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}