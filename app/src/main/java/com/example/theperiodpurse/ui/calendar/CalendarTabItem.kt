package com.example.theperiodpurse.ui.calendar

import androidx.compose.runtime.Composable
import com.example.theperiodpurse.ui.cycle.CycleScreenLayout

typealias ComposableFun = @Composable () -> Unit

sealed class CalendarTabItem(var title: String, var screen: ComposableFun) {
    object CalendarTab : CalendarTabItem("Calendar", { CalendarScreenLayout() })
    object CycleTab : CalendarTabItem("Cycle", { CycleScreenLayout() })
}
