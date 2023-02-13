package com.example.theperiodpurse.ui.calendar

import com.example.theperiodpurse.data.CrampSeverity
import com.example.theperiodpurse.data.ExerciseSeverity
import com.example.theperiodpurse.data.FlowSeverity
import com.example.theperiodpurse.data.Sleep
import java.sql.Time
import java.time.LocalDate
import kotlin.collections.LinkedHashMap

data class CalendarUIState (
   var days: LinkedHashMap<LocalDate, CalendarDayUIState>
)

data class CalendarDayUIState (
   val flow: FlowSeverity?,
   val mood: String = "",
   private val exerciseLengthString: String = "",
   val exerciseType: String = "",
   val crampSeverity: CrampSeverity?,
   private val sleepString: String = ""
) {
   val exerciseLength = if (exerciseLengthString != "") Time.valueOf(exerciseLengthString).minutes else null
   val exerciseSeverity = if (exerciseLength != null) ExerciseSeverity.fromMinutes(exerciseLength) else null
   val sleep = if (sleepString != "") Time.valueOf(sleepString) else null
   val sleepScore = if (sleep != null) Sleep.fromMinutes(sleep.minutes) else null
}
