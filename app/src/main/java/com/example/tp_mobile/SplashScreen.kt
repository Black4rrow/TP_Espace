package com.example.tp_mobile

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ImageView
import com.example.tp_mobile.views.login.LoginActivity

class SplashScreen : AppCompatActivity() {

    private val DELAY = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(0,0)
            finish()
            overridePendingTransition(0,0)
        }, DELAY.toLong())

        if(BuildConfig.FLAVOR == "develop"){
            findViewById<ImageView>(R.id.developImage).visibility = View.VISIBLE
            findViewById<ImageView>(R.id.preProdImage).visibility = View.GONE
        }else if(BuildConfig.FLAVOR == "preProd"){
            findViewById<ImageView>(R.id.developImage).visibility = View.GONE
            findViewById<ImageView>(R.id.preProdImage).visibility = View.VISIBLE
        }else if(BuildConfig.FLAVOR == "prod"){
            findViewById<ImageView>(R.id.developImage).visibility = View.GONE
            findViewById<ImageView>(R.id.preProdImage).visibility = View.GONE
        }
    }
}