package com.example.tp_mobile.views.apod

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp_mobile.model.Apod
import com.example.tp_mobile.model.domain.ApodRepository
import com.example.tp_mobile.model.domain.FireballRepository
import com.example.tp_mobile.model.domain.api.ApodApiController
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class ApodViewModel : ViewModel() {
    private val apodApiController = ApodApiController
    private val _apod = MutableLiveData<Apod>()
    val apod: LiveData<Apod> get() = _apod

    fun fetchApod()
    {
        viewModelScope.launch {
            ApodRepository.getApod().catch {
                Log.e("ApodViewModel", "Error fetching data", it)
            }.collect {
                _apod.postValue(it)
            }
        }
    }
}