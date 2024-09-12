package com.example.tp_mobile.model.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

class FireballApiController {

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://data.opendatasoft.com/explore/dataset/")
            .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    val fireballApiService: FireballApiService by lazy {
        retrofit.create(FireballApiService::class.java)
    }
}