package com.tpp.theperiodpurse.ui.calendar

import com.tpp.theperiodpurse.data.*
import java.sql.Time
import java.time.LocalDate
import kotlin.collections.LinkedHashMap

data class CalendarUIState(
    var days: LinkedHashMap<LocalDate, CalendarDayUIState>,
    var selectedSymptom: Symptom
)

data class CalendarDayUIState(
    val flow: FlowSeverity?,
    val mood: Mood? = null,
    val exerciseLengthString: String = "",
    val exerciseType: Exercise? = null,
    val crampSeverity: CrampSeverity? = null,
    val sleepString: String = ""
) {
    private val exerciseLength =
        if (exerciseLengthString != "") Time.valueOf(exerciseLengthString) else null
    val exerciseSeverity = if (exerciseLength != null) ExerciseSeverity.fromHoursAndMinutes(
        exerciseLength.hours,
        exerciseLength.minutes
    ) else null
    val sleep = if (sleepString != "") Time.valueOf(sleepString) else null
    val sleepScore =
        if (sleep != null) Sleep.fromHoursAndMinutes(sleep.hours, sleep.minutes) else null
}
