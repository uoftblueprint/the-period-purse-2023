package com.tpp.theperiodpurse.ui.symptomlog

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.FlowSeverity
import com.tpp.theperiodpurse.ui.calendar.CalendarDayUIState
import com.tpp.theperiodpurse.ui.calendar.CalendarViewModel
import com.tpp.theperiodpurse.ui.calendar.MonthHeader
import com.tpp.theperiodpurse.ui.calendar.components.Day
import com.tpp.theperiodpurse.ui.component.PopupTopBar
import com.tpp.theperiodpurse.ui.theme.Red
import com.tpp.theperiodpurse.ui.theme.SelectedColor1
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.tpp.theperiodpurse.data.Symptom
import com.tpp.theperiodpurse.ui.calendar.components.getDayColorAndIcon
import java.time.LocalDate
import java.time.YearMonth

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogMultipleDatesScreen(
    onClose: () -> Unit,
    calendarViewModel: CalendarViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        PopupTopBar(onClose = onClose) {
            LogMultipleDatesText()
        }
        val currentMonth = remember { YearMonth.now() }
        val startMonth = remember { currentMonth.minusMonths(12) } // Previous months
        val endMonth = remember { currentMonth.plusMonths(0) } // Next months
        val firstDayOfWeek = remember { firstDayOfWeekFromLocale() } // Available from the library

        val state = rememberCalendarState(
            startMonth = startMonth,
            endMonth = endMonth,
            firstVisibleMonth = currentMonth,
            firstDayOfWeek = firstDayOfWeek
        )

        val calendarDayUIStates = calendarViewModel.uiState.collectAsState().value.days

        val selectedDates = mutableSetOf<LocalDate>()
        val unselectedDates = mutableSetOf<LocalDate>()

        Box {
            VerticalCalendar(
                modifier = Modifier
                    .padding(horizontal = 12.dp)
                    .semantics { contentDescription = "Calendar" },
                contentPadding = PaddingValues(bottom = 48.dp),
                state = state,
                monthHeader = { month ->
                    MonthHeader(month)
                },
                dayContent = { day ->
                    if (day.position == DayPosition.MonthDate) {
                        val initSelected = calendarDayUIStates[day.date]?.flow != null
                        var selected by remember { mutableStateOf(initSelected) }
//                        val (dayColor, _) = getDayColorAndIcon(Symptom.FLOW, calendarDayUIStates[day.date])
                        Day(
                            day.date,
                            color = if (selected) Red else Color.White,
                            iconId = null,
                            onClick = {
                                selected = !selected
                                if (selected) {
                                    selectedDates.add(day.date)
                                    unselectedDates.remove(day.date)
                                }
                                else {
                                    selectedDates.remove(day.date)
                                    unselectedDates.add(day.date)
                                }
                            }
                        )
                    }
                })

            FloatingActionButton(onClick = {
                selectedDates.forEach {
                    calendarViewModel.updateDayInfo(it, CalendarDayUIState(flow = FlowSeverity.Medium))
                }
                unselectedDates.forEach {
                    calendarViewModel.clearFlow(it)
                }
                onClose()},
                backgroundColor = SelectedColor1,
                modifier = Modifier
                    .padding(end = 16.dp, bottom = 16.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(
                    Icons.Rounded.Check,
                    contentDescription = "Save",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
private fun LogMultipleDatesText(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            stringResource(R.string.log_multiple_dates_header_title),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = modifier
        )
        Text(
            stringResource(R.string.log_multiple_dates_header_body),
            fontSize = 13.sp,
            modifier = modifier
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun LogMultipleDatesScreenPreview() {
    LogMultipleDatesScreen({}, viewModel())
}