package com.example.tp_mobile.views.fireball

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp_mobile.model.Fireball
import com.example.tp_mobile.model.domain.FireballRepository
import com.example.tp_mobile.model.domain.api.FireballApiController
import com.example.tp_mobile.utils.SortStyle
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class FireballListViewModel : ViewModel() {
    private val fireballApiController = FireballApiController

    private val _items = MutableLiveData<List<Fireball>>()
    val items: LiveData<List<Fireball>> get() = _items

    var fireballsList: MutableList<Fireball> = emptyList<Fireball>().toMutableList()


    fun fetchFireballData(limit: Int, offset: Int, sortStyle: SortStyle?) {
        viewModelScope.launch {
            FireballRepository.getFireball(limit, offset, sortStyle).catch {
                Log.e("FireballListViewModel", "Error fetching data", it)
            }.collect {
                _items.postValue(it)
            }
        }
    }

    fun fetchFireballDataAdd(limit: Int, offset: Int, sortStyle: SortStyle?) {
        viewModelScope.launch {
            FireballRepository.getFireball(limit, offset, sortStyle).catch {
                Log.e("FireballListViewModel", "Error fetching data", it)
            }.collect {
                val newList = _items.value.orEmpty().toMutableList()
                newList.addAll(it)
                _items.postValue(newList)
            }
        }
    }
}