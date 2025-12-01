package com.okovision.android

import android.app.Application
import android.content.Context
import androidx.work.Configuration
import com.okovision.android.data.LiveRepository
import com.okovision.android.data.SettingsStore
import com.okovision.android.db.AppDb
import com.okovision.android.network.BoilerClient
import com.okovision.android.network.MqttDiscovery

class OkovisionApp : Application(), Configuration.Provider {
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()

    companion object {
        lateinit var liveRepo: LiveRepository
        lateinit var db: AppDb
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        SettingsStore.init(appContext)
        db = AppDb.build(appContext)
        liveRepo = LiveRepository(boiler = BoilerClient())
        liveRepo.start()
        MqttDiscovery.publishAll()
    }
}