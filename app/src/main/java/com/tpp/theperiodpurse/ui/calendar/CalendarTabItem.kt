package com.tpp.theperiodpurse.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.tpp.theperiodpurse.ui.cycle.CycleScreenLayout

typealias ComposableNavFun = @Composable (calendarViewModel: CalendarViewModel, navController: NavController) -> Unit

open class CalendarTabItem(var title: String, var screen: ComposableNavFun) {
    // Sealed Class to separately store the tab data from any screens main file
    @RequiresApi(Build.VERSION_CODES.O)
    object CalendarTab : CalendarTabItem(
        "Calendar",
        {calendarViewModel, navController -> CalendarScreenLayout(calendarViewModel, navController) })
    object CycleTab : CalendarTabItem("Cycle", { _, _ -> CycleScreenLayout() })
}
