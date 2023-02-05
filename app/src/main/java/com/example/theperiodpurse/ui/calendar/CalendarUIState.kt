package com.example.theperiodpurse.ui.calendar

import java.time.LocalDate
import kotlin.collections.LinkedHashMap
import kotlin.time.Duration

data class CalendarUIState (
   var days: LinkedHashMap<LocalDate, CalendarDayUIState>
)

data class CalendarDayUIState (
   val flow: String = "",
   val mood: String = "",
   val exerciseLength: Duration = Duration.ZERO,
   val exerciseType: String = "",
   val crampSeverity: String = "",
   val sleep: Duration = Duration.ZERO
)
