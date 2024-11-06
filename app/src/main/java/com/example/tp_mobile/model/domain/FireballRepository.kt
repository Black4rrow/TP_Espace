package com.example.tp_mobile.model.domain

import android.content.Context
import com.example.tp_mobile.model.Fireball
import com.example.tp_mobile.model.FireballEntity
import com.example.tp_mobile.model.domain.api.FireballApiController
import com.example.tp_mobile.model.domain.database.dao.FireballDao
import com.example.tp_mobile.utils.SortStyle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object FireballRepository {
    private lateinit var  fireballDao: FireballDao

    suspend fun getFireball(limit: Int, offset: Int, sortStyle: SortStyle?): Flow<List<Fireball>> = flow {
        val response = FireballApiController.fireballApiService.getFireballData(sortStyle?.orderValueApi,limit, offset)
        val list: List<Fireball> = response.results

        emit(list)
    }

    suspend fun insertFavorite(fireball: Fireball){
        fireballDao = AppDatabase.getInstance().fireballDao()
        val fireballEntity = fireballToEntity(fireball)
        fireballDao.insertFavorite(fireballEntity)
    }

    suspend fun removeFavorite(fireball: Fireball){
        fireballDao = AppDatabase.getInstance().fireballDao()
        val fireballEntity = fireballToEntity(fireball)
        fireballDao.deleteFavorite(fireballEntity)
    }

    suspend fun isFavorite(fireball: Fireball): Boolean {
        fireballDao = AppDatabase.getInstance().fireballDao()
        val compositeKey = fireball.getDBId()
        return fireballDao.getFavoriteById(compositeKey) != null
    }

    private fun fireballToEntity(fireball: Fireball): FireballEntity {
        return FireballEntity(
            date = fireball.date!!,
            energy = fireball.energy,
            impactE = fireball.impactE,
            vel = fireball.vel,
            alt = fireball.alt,
            lon = fireball.lon,
            lat = fireball.lat,
            lonDir = fireball.lonDir,
            latDir = fireball.latDir,
        )
    }


}