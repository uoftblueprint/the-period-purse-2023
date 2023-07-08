package com.tpp.theperiodpurse.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.kizitonwose.calendar.core.CalendarDay
import com.tpp.theperiodpurse.data.model.Symptom
import com.tpp.theperiodpurse.ui.calendar.components.Day
import com.tpp.theperiodpurse.ui.state.CalendarDayUIState

// Creates the days
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarDay(
    day: CalendarDay,
    calendarDayUIState: CalendarDayUIState?,
    activeSymptom: Symptom,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val (dayColor, iconId) = getDayColorAndIcon(activeSymptom, calendarDayUIState)
    Day(day.date, dayColor, iconId, onClick, modifier)
}
