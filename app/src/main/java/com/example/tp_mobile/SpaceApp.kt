package com.example.tp_mobile

import AppDatabase
import android.app.Application
import timber.log.Timber

class SpaceApp: Application()  {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        AppDatabase.initDatabase(context = this)

    }
}