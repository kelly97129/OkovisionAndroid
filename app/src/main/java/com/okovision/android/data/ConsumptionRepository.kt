package com.okovision.android.data

import com.okovision.android.OkovisionApp
import com.okovision.android.db.ConsumptionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.BufferedReader
import java.io.StringReader
import java.time.LocalDate

class ConsumptionRepository {
    private val dao = OkovisionApp.db.consumptionDao()
    val all: Flow<List<ConsumptionEntity>> = dao.getAll()
    private val http = OkHttpClient()

    suspend fun importCsvText(csv: String) = withContext(Dispatchers.IO) {
        val out = mutableListOf<ConsumptionEntity>()
        BufferedReader(StringReader(csv)).useLines { lines ->
            val it = lines.iterator()
            if (!it.hasNext()) return@useLines
            it.next() // skip header
            while (it.hasNext()) {
                val row = it.next().trim()
                if (row.isEmpty()) continue
                val parts = row.split(';', ',', '\t')
                val date = parts.getOrNull(0) ?: continue
                val kg = parts.getOrNull(1)?.replace(',', '.')?.toDoubleOrNull() ?: continue
                val cost = parts.getOrNull(2)?.replace(',', '.')?.toDoubleOrNull()
                out += ConsumptionEntity(date, kg, cost)
            }
        }
        dao.upsertAll(out)
        val today = LocalDate.now().toString()
        val todayRow = out.firstOrNull { it.dateIso == today }
        todayRow?.let {
            val json = "{\"date\":\"${it.dateIso}\",\"kg\":${it.kg}}"
            com.okovision.android.network.MqttBus.publish("okovision/consumption", json, retain = true, qos = 1)
        }
    }

    suspend fun importFromUrl(url: String) {
        val req = Request.Builder().url(url).get().build()
        http.newCall(req).execute().use { resp ->
            val body = resp.body?.string().orEmpty()
            if (resp.isSuccessful && body.isNotBlank()) importCsvText(body)
        }
    }

    suspend fun clear() = withContext(Dispatchers.IO) { dao.clear() }
}