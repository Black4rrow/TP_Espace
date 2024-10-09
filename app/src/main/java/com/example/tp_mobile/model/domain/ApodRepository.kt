package com.example.tp_mobile.model.domain

import com.example.tp_mobile.model.Apod
import com.example.tp_mobile.model.domain.api.ApodApiController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object ApodRepository {
    suspend fun getApod(): Flow<Apod> = flow {
        val response = ApodApiController.apodApiService.getApod(ApodApiController.API_KEY)
        val apod: Apod = response

        emit(apod)
    }
}