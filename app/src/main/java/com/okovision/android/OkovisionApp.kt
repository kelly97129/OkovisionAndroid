package com.okovision.android

import android.app.Application
import androidx.work.Configuration
import com.okovision.android.data.LiveRepository

class OkovisionApp : Application(), Configuration.Provider {

    lateinit var liveRepository: LiveRepository
        private set

    override fun onCreate() {
        super.onCreate()
        liveRepository = LiveRepository(applicationContext)
        liveRepository.start()
    }

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .build()
}
