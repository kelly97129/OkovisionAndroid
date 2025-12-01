package com.okovision.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.okovision.android.data.SettingsStore

@Composable
fun ScreenSettings() {
    val current = SettingsStore.settings.collectAsState().value
    var haUrl by remember { mutableStateOf(current.haUrl) }
    var haToken by remember { mutableStateOf(current.haToken) }
    var mqttBroker by remember { mutableStateOf(current.mqttBroker) }
    var mqttUser by remember { mutableStateOf(current.mqttUser ?: "") }
    var mqttPass by remember { mutableStateOf(current.mqttPass ?: "") }
    var okovisionUrl by remember { mutableStateOf(current.okovisionUrl) }
    var boilerEndpoint by remember { mutableStateOf(current.boilerEndpoint) }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Réglages", style = MaterialTheme.typography.headlineSmall)
        OutlinedTextField(haUrl, { haUrl = it }, label = { Text("Home Assistant URL") }, singleLine = true)
        OutlinedTextField(haToken, { haToken = it }, label = { Text("Home Assistant Token") }, singleLine = true)
        OutlinedTextField(mqttBroker, { mqttBroker = it }, label = { Text("MQTT Broker") }, singleLine = true)
        OutlinedTextField(mqttUser, { mqttUser = it }, label = { Text("MQTT User (optionnel)") }, singleLine = true)
        OutlinedTextField(mqttPass, { mqttPass = it }, label = { Text("MQTT Pass (optionnel)") }, singleLine = true)
        OutlinedTextField(okovisionUrl, { okovisionUrl = it }, label = { Text("URL Okovision (CSV export)") }, singleLine = true)
        OutlinedTextField(boilerEndpoint, { boilerEndpoint = it }, label = { Text("API Chaudière (JSON / key=value / CSV)") }, singleLine = true)
        Button(onClick = {
            SettingsStore.update {
                it.copy(
                    haUrl = haUrl.trim(),
                    haToken = haToken.trim(),
                    mqttBroker = mqttBroker.trim(),
                    mqttUser = mqttUser.trim().ifEmpty { null },
                    mqttPass = mqttPass.trim().ifEmpty { null },
                    okovisionUrl = okovisionUrl.trim(),
                    boilerEndpoint = boilerEndpoint.trim()
                )
            }
        }) { Text("Enregistrer") }
    }
}