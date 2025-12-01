package com.okovision.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.okovision.android.data.ConsumptionRepository
import com.okovision.android.db.ConsumptionEntity
import kotlinx.coroutines.launch
import java.nio.charset.Charset

@Composable
fun ScreenConsumption() {
    val repo = remember { ConsumptionRepository() }
    val scope = rememberCoroutineScope()
    val list by repo.all.collectAsState(initial = emptyList())

    Column(Modifier.fillMaxSize()) {
        Row(Modifier.fillMaxWidth().padding(16.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = {
                scope.launch {
                    val ctx = androidx.compose.ui.platform.LocalContext.current
                    val data = ctx.assets.open("sample_okovision.csv").readBytes().toString(Charset.defaultCharset())
                    repo.importCsvText(data)
                }
            }) { Text("Importer (sample)") }
            OutlinedButton(onClick = { scope.launch { repo.clear() } }) { Text("Vider") }
        }
        LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(list) { row: ConsumptionEntity ->
                Card { ListItem(headlineContent = { Text(row.dateIso) }, supportingContent = { Text("${row.kg} kg" + (row.cost?.let { " — ${it} €" } ?: "")) }) }
            }
        }
    }
}