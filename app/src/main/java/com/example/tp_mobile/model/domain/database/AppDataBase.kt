package com.example.tp_mobile.model.domain.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.tp_mobile.model.FireballEntity
import com.example.tp_mobile.model.domain.database.dao.FireballDao

@Database(entities = [FireballEntity::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun fireballDao(): FireballDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun initDatabase(context: Context) {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            context.applicationContext,
                            AppDatabase::class.java,
                            "space-db"
                        ).fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
        }

        fun getInstance(): AppDatabase {
            return instance
                ?: throw IllegalStateException("Database has not been initialized. Call initDatabase() first.")
        }
    }
}
