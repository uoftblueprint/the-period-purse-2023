package com.example.theperiodpurse.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import com.example.theperiodpurse.ui.cycle.CycleScreenLayout

typealias ComposableFun = @Composable () -> Unit

sealed class CalendarTabItem(var title: String, var screen: ComposableFun) {
    // Sealed Class to separately store the tab data from any screens main file
    @RequiresApi(Build.VERSION_CODES.O)
    object CalendarTab : CalendarTabItem("Calendar", { CalendarScreenLayout() })
    object CycleTab : CalendarTabItem("Cycle", { CycleScreenLayout() })
}
