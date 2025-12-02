package com.okovision.android

import android.app.Application
import androidx.room.Room
import com.okovision.android.db.AppDb

class OkovisionApp : Application() {

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            applicationContext,
            AppDb::class.java,
            "okovision.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    companion object {
        lateinit var db: AppDb
            private set
    }
}
