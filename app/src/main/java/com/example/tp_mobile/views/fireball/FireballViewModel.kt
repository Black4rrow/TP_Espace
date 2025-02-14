package com.example.tp_mobile.views.fireball

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp_mobile.model.Fireball
import com.example.tp_mobile.model.domain.FireballRepository
import com.example.tp_mobile.utils.SortStyle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class FireballListViewModel : ViewModel() {

    private val _items = MutableLiveData<List<Fireball>>()
    val items: LiveData<List<Fireball>> get() = _items

    private val _secondItems = MutableLiveData<List<Fireball>>()
    val secondItems: LiveData<List<Fireball>> get() = _secondItems

    fun fetchFireballData(limit: Int, offset: Int, sortStyle: SortStyle?) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        viewModelScope.launch {
            FireballRepository.getFireball(limit, offset, sortStyle).catch {
                Log.e("FireballListViewModel", "Error fetching data", it)
            }.collect {
                if(currentUser != null){
                    for(fireball in it){
                        if(FireballRepository.isFavorite(fireball)){
                            fireball.isFavorite = true
                        }
                    }
                }
                _items.postValue(it)
            }
        }
    }

    fun fetchFireballDataForTwo(limitFrist: Int, offsetFrist: Int, sortStyleFrist: SortStyle?,limitSecond: Int, offsetSecond: Int, sortStyleSecond: SortStyle?) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        viewModelScope.launch {
            FireballRepository.getFireball(limitFrist, offsetFrist, sortStyleFrist).catch {
                Log.e("FireballListViewModel", "Error fetching data", it)
            }.collect {
                if (currentUser != null) {
                    for (fireball in it) {
                        if (FireballRepository.isFavorite(fireball)) {
                            fireball.isFavorite = true
                        }
                    }
                }
                _items.postValue(it)
            }
        }
        viewModelScope.launch {
            FireballRepository.getFireball(limitSecond, offsetSecond, sortStyleSecond).catch {
                Log.e("FireballListViewModel", "Error fetching data", it)
            }.collect {
                if (currentUser != null) {
                    for (fireball in it) {
                        if (FireballRepository.isFavorite(fireball)) {
                            fireball.isFavorite = true
                        }
                    }
                }
                _secondItems.postValue(it)
            }
        }

    }

    fun fetchFireballDataAdd(limit: Int, offset: Int, sortStyle: SortStyle?) {
        val firebaseAuth = FirebaseAuth.getInstance()
        val currentUser = firebaseAuth.currentUser

        viewModelScope.launch {
            FireballRepository.getFireball(limit, offset, sortStyle).catch {
                Log.e("FireballListViewModel", "Error fetching data", it)
            }.collect {
                if(currentUser != null){
                    for(fireball in it){
                        if(FireballRepository.isFavorite(fireball)){
                            fireball.isFavorite = true
                        }
                    }
                }
                val newList = _items.value.orEmpty().toMutableList()
                newList.addAll(it)
                _items.postValue(newList)
            }
        }
    }

    fun addFavorite(fireball: Fireball) {
        viewModelScope.launch {
            FireballRepository.insertFavorite(fireball)
        }
    }

    fun removeFavorite(fireball: Fireball) {
        viewModelScope.launch {
            FireballRepository.removeFavorite(fireball)
        }
    }

    fun isFavorite(fireball: Fireball): LiveData<Boolean> {
        val isFavorite = MutableLiveData<Boolean>()
        viewModelScope.launch {
            isFavorite.postValue(FireballRepository.isFavorite(fireball))
        }
        return isFavorite
    }

    fun getFavorites(): LiveData<List<Fireball>> {
        val favorites = MutableLiveData<List<Fireball>>()
        viewModelScope.launch {
            favorites.postValue(FireballRepository.getFavorites())
        }
        return favorites
    }

}