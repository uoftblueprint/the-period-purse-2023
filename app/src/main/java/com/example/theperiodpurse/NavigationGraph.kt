package com.example.theperiodpurse

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.theperiodpurse.ui.calendar.CalendarScreen
import com.example.theperiodpurse.ui.cycle.CycleScreenLayout
import com.example.theperiodpurse.ui.setting.SettingScreen

enum class Screen() {
    Calendar,
    Cycle,
    Settings,
    Learn
}

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Calendar.name,
    ) {
        composable(route = Screen.Calendar.name) {
            CalendarScreen()
        }

        composable(route = Screen.Settings.name) {
            SettingScreen()
        }

        composable(route = Screen.Cycle.name) {
            CycleScreenLayout()
        }

        composable(route = Screen.Learn.name) {
            CycleScreenLayout()
        }
    }
}
