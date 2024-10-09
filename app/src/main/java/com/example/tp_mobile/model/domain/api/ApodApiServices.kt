package com.example.tp_mobile.model.domain.api

import com.example.tp_mobile.model.Apod
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApiServices {
    @GET("apod")
    suspend fun getApod(
        @Query("api_key") value: String,
    ): Apod
}