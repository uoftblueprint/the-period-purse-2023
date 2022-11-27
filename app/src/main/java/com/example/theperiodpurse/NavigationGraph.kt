package com.example.theperiodpurse

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.theperiodpurse.calendar.CalendarScreen
import com.example.theperiodpurse.cycle.CycleScreen
import com.example.theperiodpurse.setting.SettingScreen

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Calendar.name,
    ) {
        composable(route = AppScreen.Calendar.name) {
            CalendarScreen()
        }

        composable(route = AppScreen.Settings.name) {
            SettingScreen()
        }

        composable(route = AppScreen.Cycle.name) {
            CycleScreen()
        }

        composable(route = AppScreen.Learn.name) {
            CycleScreen()
        }
    }
}
