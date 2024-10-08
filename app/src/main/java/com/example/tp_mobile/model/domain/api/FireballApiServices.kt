package com.example.tp_mobile.model.domain.api

import com.example.example.FireballApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FireballApiService {
    @GET("records")
    suspend fun getFireballData(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): FireballApiResponse
}