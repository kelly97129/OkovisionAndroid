package com.okovision.android.data

import kotlinx.serialization.Serializable

@Serializable
data class ConsumptionEntry(
    val dateIso: String,
    val kg: Double,
    val cost: Double? = null
)

@Serializable
data class BoilerLive(
    val waterTempC: Double? = null,
    val pelletLevelPct: Double? = null,
    val status: String? = null
)