package com.example.tp_mobile.model.domain

import com.example.tp_mobile.model.Fireball
import com.example.tp_mobile.model.domain.api.FireballApiController
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


object FireballRepository {

    suspend fun getFireball(limit: Int, offset: Int): Flow<List<Fireball>> = flow {
        val response = FireballApiController.fireballApiService.getFireballData(limit, offset)
        val list: List<Fireball> = response.results

        emit(list)
    }
}