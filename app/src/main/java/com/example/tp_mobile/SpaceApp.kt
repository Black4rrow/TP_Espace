package com.example.tp_mobile

import android.app.Application
import com.example.tp_mobile.model.domain.database.AppDatabase
import timber.log.Timber

class SpaceApp: Application()  {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        AppDatabase.initDatabase(context = this)

    }
}