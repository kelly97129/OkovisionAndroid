package com.okovision.android.data

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val STORE_NAME = "okovision_settings"
private val Context.dataStore by preferencesDataStore(name = STORE_NAME)

object SettingsKeys {
    val OKOVISION_URL = stringPreferencesKey("okovision_url")
    val BOILER_URL = stringPreferencesKey("boiler_url")
    val MQTT_HOST = stringPreferencesKey("mqtt_host")
    val MQTT_USER = stringPreferencesKey("mqtt_user")
    val MQTT_PASS = stringPreferencesKey("mqtt_pass")
}

class Settings(private val context: Context) {

    fun getString(key: Preferences.Key<String>, default: String = ""): Flow<String> =
        context.dataStore.data.map { prefs -> prefs[key] ?: default }

    suspend fun putString(key: Preferences.Key<String>, value: String) {
        context.dataStore.edit { prefs ->
            prefs[key] = value
        }
    }
}
