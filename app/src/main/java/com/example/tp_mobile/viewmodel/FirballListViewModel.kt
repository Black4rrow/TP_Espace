package com.example.tp_mobile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp_mobile.model.retrofit.FireballApiController
import kotlinx.coroutines.launch

class FireballListViewModel : ViewModel() {
    private val fireballApiController = FireballApiController()

    fun fetchFireballData() {
        viewModelScope.launch {
            try {
                val response = fireballApiController.fireballApiService.getFireballData(20)
                Log.e("Response", response.toString())
            } catch (e: Exception) {
                Log.e("Error", e.toString())
            }
        }
    }
}