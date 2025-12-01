package com.okovision.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.okovision.android.network.MqttBus

@Composable
fun ScreenStocks() {
    var sacksOnPallet by remember { mutableStateOf(72) }
    var sacksLeft by remember { mutableStateOf(50) }
    var bagWeightKg by remember { mutableStateOf(15) }

    fun publishStocks() {
        val json = """{"sacksLeft":$sacksLeft,"bagKg":$bagWeightKg,"pallet":$sacksOnPallet}"""
        MqttBus.publish("okovision/stocks", json, retain = true, qos = 1)
    }

    LaunchedEffect(Unit) { publishStocks() }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Stocks", style = MaterialTheme.typography.headlineSmall)
        Card { ListItem(headlineContent = { Text("Palette initiale") }, supportingContent = { Text("$sacksOnPallet sacs") }) }
        Card { 
            ListItem(
                headlineContent = { Text("Sacs restants") },
                supportingContent = { Text("$sacksLeft sacs") },
                trailingContent = { 
                    Row { 
                        TextButton(onClick = { if (sacksLeft>0) { sacksLeft--; publishStocks() } }){ Text("-1") }
                        TextButton(onClick = { if (sacksLeft>=7) { sacksLeft-=7; publishStocks() } }){ Text("-7") }
                    } 
                }
            )
        }
        Card { ListItem(headlineContent = { Text("Poids sac") }, supportingContent = { Text("$bagWeightKg kg") }) }
        Text("Actions rapides")
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { sacksLeft = sacksOnPallet; publishStocks() }) { Text("Réinitialiser palette") }
            Button(onClick = { sacksLeft = maxOf(0, sacksLeft-1); publishStocks() }) { Text("— 1 sac") }
            Button(onClick = { sacksLeft = maxOf(0, sacksLeft-7); publishStocks() }) { Text("— 7 sacs") }
        }
    }
}