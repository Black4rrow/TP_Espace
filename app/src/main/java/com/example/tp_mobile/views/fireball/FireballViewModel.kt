package com.example.tp_mobile.views.fireball

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.example.Fireball
import com.example.tp_mobile.model.retrofit.FireballApiController
import kotlinx.coroutines.launch

class FireballListViewModel : ViewModel() {
    private val fireballApiController = FireballApiController()

    private val _items = MutableLiveData<List<Fireball>>()
    val items: LiveData<List<Fireball>> get() = _items

    var fireballsList : MutableList<Fireball> = emptyList<Fireball>().toMutableList()

    init {
        fetchFireballData()
    }

    fun fetchFireballData(limit: Int = 20, offset: Int = 0) {
        viewModelScope.launch {
            try {
                val response = fireballApiController.fireballApiService.getFireballData(limit, offset)
                fireballsList.addAll(response.results)

                _items.value = fireballsList
            } catch (e: Exception) {
            }
        }
    }
}