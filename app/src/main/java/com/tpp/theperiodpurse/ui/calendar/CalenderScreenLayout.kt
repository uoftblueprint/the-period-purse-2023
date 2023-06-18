package com.tpp.theperiodpurse.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.model.Symptom
import com.tpp.theperiodpurse.navigateToLogScreenWithDate
import com.tpp.theperiodpurse.ui.calendar.components.MonthHeader
import com.tpp.theperiodpurse.ui.calendar.components.SymptomTab
import com.tpp.theperiodpurse.ui.theme.ThePeriodPurseTheme
import com.tpp.theperiodpurse.ui.viewmodel.AppViewModel
import com.tpp.theperiodpurse.ui.viewmodel.CalendarViewModel
import java.time.YearMonth


val previewTrackedSymptoms = Symptom.values().asList()

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreenLayout(
    calendarViewModel: CalendarViewModel,
    navController: NavController,
    appViewModel: AppViewModel
) {
    val calendarUIState by calendarViewModel.uiState.collectAsState()
    // Contains the swappable content
    ThePeriodPurseTheme {
        val bg = painterResource(R.drawable.colourwatercolour)
        val appUiState by appViewModel.uiState.collectAsState()

        val selectedSymptom = calendarUIState.selectedSymptom
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
        var calendarContentDescription = stringResource(id = R.string.calendar_page)
        Box {
            Image(
                painter = bg,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .semantics { contentDescription = calendarContentDescription },
                contentScale = ContentScale.FillBounds,
            )
            Column {
                SymptomTab(
                    selectedSymptom = selectedSymptom,
                    onSymptomClick = { calendarViewModel.setSelectedSymptom(it) },
                    trackedSymptoms = appUiState.trackedSymptoms
                )
                calendarContentDescription = stringResource(id = R.string.calendar)
                VerticalCalendar(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .semantics { contentDescription = calendarContentDescription },
                    contentPadding = PaddingValues(bottom = 120.dp),
                    state = state,
                    monthHeader = { month ->
                        MonthHeader(month)
                    },
                    dayContent = { day ->
                        if (day.position == DayPosition.MonthDate) {
                            CalendarDay(
                                day = day,
                                calendarDayUIState = calendarUIState.days[day.date],
                                activeSymptom = selectedSymptom,
                                onClick = { navigateToLogScreenWithDate(day.date, navController) }
                            )
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun DisplaySymptomTabPreview() {
    SymptomTab(trackedSymptoms = previewTrackedSymptoms, Symptom.FLOW, {})
}
