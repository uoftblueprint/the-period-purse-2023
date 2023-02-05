package com.example.theperiodpurse.ui.calendar

import java.sql.Time
import java.time.LocalDate
import kotlin.collections.LinkedHashMap

data class CalendarUIState (
   var days: LinkedHashMap<LocalDate, CalendarDayUIState>
)

data class CalendarDayUIState (
   val flow: String = "",
   val mood: String = "",
   private val exerciseLengthString: String = "",
   val exerciseType: String = "",
   val crampSeverity: String = "",
   private val sleepString: String = ""
) {
   val exerciseLength = if (exerciseLengthString != "") Time.valueOf(exerciseLengthString) else null
   val sleep = if (sleepString != "") Time.valueOf(sleepString) else null
}
