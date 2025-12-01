package com.okovision.android.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class Settings(
    val haUrl: String = "http://homeassistant.local:8123",
    val haToken: String = "LONG_LIVED_TOKEN",
    val mqttBroker: String = "tcp://192.168.0.203:1883",
    val mqttUser: String? = null,
    val mqttPass: String? = null,
    val okovisionUrl: String = "https://okovision.dronek.com/",
    val boilerEndpoint: String = "http://192.168.0.50:4321/live"
)

private val Context.dataStore by preferencesDataStore(name = "okovision_settings")

object SettingsStore {
    private lateinit var ctx: Context
    private val scope = CoroutineScope(Dispatchers.IO + Job())

    private val K_HA_URL = stringPreferencesKey("ha_url")
    private val K_HA_TOKEN = stringPreferencesKey("ha_token")
    private val K_MQTT_BROKER = stringPreferencesKey("mqtt_broker")
    private val K_MQTT_USER = stringPreferencesKey("mqtt_user")
    private val K_MQTT_PASS = stringPreferencesKey("mqtt_pass")
    private val K_OKO_URL = stringPreferencesKey("okovision_url")
    private val K_BOILER = stringPreferencesKey("boiler_endpoint")

    private val _settings = MutableStateFlow(Settings())
    val settings: StateFlow<Settings> = _settings

    fun init(context: Context) {
        ctx = context.applicationContext
        scope.launch { _settings.value = readFrom(ctx.dataStore.data.first()) }
        scope.launch { ctx.dataStore.data.collect { _settings.value = readFrom(it) } }
    }

    private fun readFrom(prefs: Preferences): Settings = Settings(
        haUrl = prefs[K_HA_URL] ?: Settings().haUrl,
        haToken = prefs[K_HA_TOKEN] ?: Settings().haToken,
        mqttBroker = prefs[K_MQTT_BROKER] ?: Settings().mqttBroker,
        mqttUser = prefs[K_MQTT_USER],
        mqttPass = prefs[K_MQTT_PASS],
        okovisionUrl = prefs[K_OKO_URL] ?: Settings().okovisionUrl,
        boilerEndpoint = prefs[K_BOILER] ?: Settings().boilerEndpoint
    )

    fun update(block: (Settings) -> Settings) {
        val newVal = block(_settings.value)
        scope.launch {
            ctx.dataStore.edit { e ->
                e[K_HA_URL] = newVal.haUrl
                e[K_HA_TOKEN] = newVal.haToken
                e[K_MQTT_BROKER] = newVal.mqttBroker
                if (newVal.mqttUser != null) e[K_MQTT_USER] = newVal.mqttUser!! else e.remove(K_MQTT_USER)
                if (newVal.mqttPass != null) e[K_MQTT_PASS] = newVal.mqttPass!! else e.remove(K_MQTT_PASS)
                e[K_OKO_URL] = newVal.okovisionUrl
                e[K_BOILER] = newVal.boilerEndpoint
            }
        }
    }
}