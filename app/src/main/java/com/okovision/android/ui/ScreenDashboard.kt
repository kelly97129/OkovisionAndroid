package com.okovision.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.okovision.android.OkovisionApp

@Composable
fun ScreenDashboard() {
    val live by OkovisionApp.liveRepo.live.collectAsState()
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Tableau de bord", style = MaterialTheme.typography.headlineSmall)
        Card { ListItem(headlineContent = { Text("Température chaudière (live)") }, supportingContent = { Text(live.waterTempC?.let { String.format("%.1f °C", it) } ?: "-- °C") }) }
        Card { ListItem(headlineContent = { Text("Conso jour (kg)") }, supportingContent = { Text("-- kg") }) }
        Card { ListItem(headlineContent = { Text("Sacs restants (palette)") }, supportingContent = { Text("-- / --") }) }
    }
}