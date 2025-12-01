package com.okovision.android.data

import android.content.Context
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class LiveState(
    val waterTempC: Float? = null
)

class LiveRepository(@Suppress("UNUSED_PARAMETER") context: Context) {
    private val _state = MutableStateFlow(LiveState())
    val state: StateFlow<LiveState> = _state

    fun start() {
        // Stub: à remplacer par la vraie lecture chaudière / MQTT / HA
    }
}
