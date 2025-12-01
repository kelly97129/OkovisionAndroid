package com.okovision.android.ui

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.viewinterop.AndroidView
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.okovision.android.OkovisionApp
import com.okovision.android.data.ConsumptionRepository
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState

@Composable
fun ScreenCharts() {
    val live by OkovisionApp.liveRepo.live.collectAsState()
    val repo = remember { ConsumptionRepository() }
    val list by repo.all.collectAsState(initial = emptyList())
    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Courbes", style = MaterialTheme.typography.headlineSmall)

        AndroidView(factory = { ctx ->
            LineChart(ctx).apply {
                description.isEnabled = false
                legend.form = Legend.LegendForm.LINE
                setTouchEnabled(true)
                setPinchZoom(true)
                axisRight.isEnabled = false
            }
        }, update = { chart ->
            val existing = (chart.data?.getDataSetByIndex(0) as? LineDataSet)
            val dataSet = existing ?: LineDataSet(mutableListOf(), "Température (°C)").apply {
                lineWidth = 2f
                setDrawCircles(false)
                setDrawValues(false)
            }
            val x = (dataSet.entryCount).toFloat()
            live.waterTempC?.let { dataSet.addEntry(Entry(x, it.toFloat())) }
            val data = chart.data ?: LineData(dataSet).also { chart.data = it }
            if (existing == null) data.addDataSet(dataSet)
            data.notifyDataChanged()
            chart.notifyDataSetChanged()
            chart.invalidate()
        }, modifier = Modifier.fillMaxWidth().height(220.dp))

        AndroidView(factory = { ctx ->
            LineChart(ctx).apply {
                description.isEnabled = false
                legend.form = Legend.LegendForm.LINE
                setTouchEnabled(true)
                setPinchZoom(true)
                axisRight.isEnabled = false
            }
        }, update = { chart ->
            val entries = list.sortedBy { it.dateIso }.mapIndexed { idx, e -> Entry(idx.toFloat(), e.kg.toFloat()) }
            val set = LineDataSet(entries, "Conso (kg/j)").apply {
                lineWidth = 2f
                setDrawCircles(false)
                setDrawValues(false)
            }
            chart.data = LineData(set)
            chart.invalidate()
        }, modifier = Modifier.fillMaxWidth().height(220.dp))
    }
}