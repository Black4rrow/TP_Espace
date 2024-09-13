package com.example.tp_mobile.views.fireball

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
            } catch (e: Exception) {
            }
        }
    }
}