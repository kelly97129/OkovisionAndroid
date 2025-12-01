package com.okovision.android.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsumptionDao {
    @Query("SELECT * FROM consumption ORDER BY dateIso ASC")
    fun getAll(): Flow<List<ConsumptionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAll(items: List<ConsumptionEntity>)

    @Query("DELETE FROM consumption")
    suspend fun clear()
}