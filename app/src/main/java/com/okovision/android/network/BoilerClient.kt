package com.okovision.android.network

import com.okovision.android.data.BoilerLive
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.BufferedReader
import java.io.StringReader

class BoilerClient(
    private val http: OkHttpClient = OkHttpClient()
) {
    suspend fun fetchLive(url: String): BoilerLive = withContext(Dispatchers.IO) {
        val req = Request.Builder().url(url).get().build()
        http.newCall(req).execute().use { resp ->
            val body = resp.body?.string().orEmpty().trim()
            if (!resp.isSuccessful || body.isEmpty()) return@use BoilerLive()

            // Try JSON
            runCatching {
                val o = JSONObject(body)
                return@use BoilerLive(
                    waterTempC = o.optDouble("waterTempC", Double.NaN).let { if (it.isNaN()) null else it },
                    pelletLevelPct = o.optDouble("pelletLevelPct", Double.NaN).let { if (it.isNaN()) null else it },
                    status = o.optString("status", null)
                )
            }

            // key=value
            val kv = HashMap<String,String>()
            BufferedReader(StringReader(body)).useLines { lines ->
                lines.forEach { line ->
                    val idx = line.indexOf('=')
                    if (idx>0) kv[line.substring(0,idx).trim()] = line.substring(idx+1).trim()
                }
            }
            if (kv.isNotEmpty()) {
                val temp = kv["waterTempC"]?.toDoubleOrNull() ?: kv["temp"]?.toDoubleOrNull()
                val level = kv["pelletLevelPct"]?.toDoubleOrNull() ?: kv["pellet"]?.toDoubleOrNull()
                val status = kv["status"]
                return@use BoilerLive(temp, level, status)
            }

            // CSV headers: date;waterTempC;pelletLevelPct;status
            val reader = BufferedReader(StringReader(body))
            val header = reader.readLine() ?: return@use BoilerLive()
            val cols = header.split(';', ',', '\t')
            if (cols.size >= 2) {
                val last = reader.readLine() ?: return@use BoilerLive()
                val v = last.split(';', ',', '\t')
                fun idx(name: String) = cols.indexOfFirst { it.equals(name, ignoreCase = true) }
                val tIdx = idx("waterTempC")
                val pIdx = idx("pelletLevelPct")
                val sIdx = idx("status")
                val temp = if (tIdx>=0 && tIdx < v.size) v[tIdx].toDoubleOrNull() else null
                val level = if (pIdx>=0 && pIdx < v.size) v[pIdx].toDoubleOrNull() else null
                val status = if (sIdx>=0 && sIdx < v.size) v[sIdx] else null
                return@use BoilerLive(temp, level, status)
            }
            return@use BoilerLive()
        }
    }
}