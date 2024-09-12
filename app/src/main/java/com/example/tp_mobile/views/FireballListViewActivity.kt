package com.example.tp_mobile.views

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.tp_mobile.R
import com.example.tp_mobile.model.retrofit.FireballApiController
import com.example.tp_mobile.viewmodel.FireballListViewModel

class FireballListViewActivity : AppCompatActivity() {

    val fireballApiController = FireballApiController()
    private lateinit var viewModel: FireballListViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fireball_list_view_activuty)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(FireballListViewModel::class.java)
        viewModel.fetchFireballData()

    }

}
