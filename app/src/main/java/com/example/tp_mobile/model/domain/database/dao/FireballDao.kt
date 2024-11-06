package com.example.tp_mobile.model.domain.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp_mobile.model.FireballEntity

@Dao
interface FireballDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(fireball: FireballEntity)

    @Delete
    suspend fun deleteFavorite(fireball: FireballEntity)

    @Query("SELECT * FROM favorites WHERE compositeKey = :compositeKey LIMIT 1")
    suspend fun getFavoriteById(compositeKey: String): FireballEntity?
}
