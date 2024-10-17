package com.example.tp_mobile.model.domain.api

import com.example.tp_mobile.model.FireballApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FireballApiService {
    @GET("records")
    suspend fun getFireballData(
        @Query("order_by") orderBy: String? = null,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ): FireballApiResponse
}