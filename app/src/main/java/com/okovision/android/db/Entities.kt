package com.okovision.android.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "consumption")
data class ConsumptionEntity(
    @PrimaryKey val dateIso: String,
    val kg: Double,
    val cost: Double?
)