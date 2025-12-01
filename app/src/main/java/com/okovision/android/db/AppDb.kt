package com.okovision.android.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ConsumptionEntity::class], version = 1, exportSchema = false)
abstract class AppDb : RoomDatabase() {
    abstract fun consumptionDao(): ConsumptionDao

    companion object {
        fun build(ctx: Context): AppDb =
            Room.databaseBuilder(ctx, AppDb::class.java, "okovision.db").fallbackToDestructiveMigration().build()
    }
}