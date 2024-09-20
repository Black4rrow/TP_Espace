package com.example.tp_mobile.model.domain.api

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object FireballApiController {

    private val BASE_URL =
        "https://data.opendatasoft.com/api/explore/v2.1/catalog/datasets/nasa-fireball-and-bolide-reports@datastro/"

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    private val retrofit: Retrofit by lazy {
        val logger = HttpLoggingInterceptor()
        logger.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .protocols(listOf(Protocol.HTTP_1_1))
            .addInterceptor(logger)
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val fireballApiService: FireballApiService by lazy {
        retrofit.create(FireballApiService::class.java)
    }
}