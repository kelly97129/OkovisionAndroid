package com.okovision.android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OkovisionRoot() {
    val navController = rememberNavController()
    val items = listOf("Tableau", "Consommation", "Courbes", "Stocks", "Réglages")
    Scaffold(
        bottomBar = {
            NavigationBar {
                val current = navController.currentBackStackEntryAsState().value?.destination?.route
                items.forEach { route ->
                    NavigationBarItem(
                        selected = current == route,
                        onClick = { navController.navigate(route) { launchSingleTop = true } },
                        label = { Text(route) },
                        icon = { Icon(Icons.Outlined.FavoriteBorder, contentDescription = null) }
                    )
                }
            }
        }
    ) { padding ->
        NavHost(
            navController = navController,
            startDestination = "Tableau",
            modifier = Modifier.fillMaxSize().padding(padding)
        ) {
            composable("Tableau") { ScreenDashboard() }
            composable("Consommation") { ScreenConsumption() }
            composable("Courbes") { ScreenCharts() }
            composable("Stocks") { ScreenStocks() }
            composable("Réglages") { ScreenSettings() }
        }
    }
}