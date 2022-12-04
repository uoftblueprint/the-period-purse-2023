package com.example.theperiodpurse.ui.calendar

import android.os.Build
// import android.os.Bundle
// import androidx.activity.ComponentActivity
// import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme
import com.google.accompanist.pager.*
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<CalendarTabItem>, pagerState: PagerState) {
    // Composable for tabs
    // Tabs are displayed in ordered row by tabs list
    // PagerState allows for navigation/animation between paginated layouts
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = Color.LightGray,
        contentColor = Color.Black,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(
                    pagerState,
                    tabPositions),
                color = Color(0xff5a9f93)
            )
        }
    ) {
        tabs.forEachIndexed { index, tab ->
            Tab(
                text = { Text(tab.title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                }
            )
        }
        
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(tabs: List<CalendarTabItem>, pagerState: PagerState) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen()
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarScreen() {
    // Main calendar screen which allows navigation to cycle page and calendar
    // By default, opens on to the Calendar
    val tabs = listOf(
        CalendarTabItem.CalendarTab,
        CalendarTabItem.CycleTab
    )
    val pagerState = rememberPagerState()
    ThePeriodPurseTheme {
        Scaffold (topBar = {})
        { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Tabs(tabs = tabs, pagerState = pagerState)
                TabsContent(tabs = tabs, pagerState = pagerState)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreenLayout() {
    // Contains the swappable content
    ThePeriodPurseTheme {
        val bg = painterResource(R.drawable.colourwatercolour)

        var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
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

        Box {
            Image(painter = bg,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
            )
            Column {
                VerticalCalendar(
                    state = state,
                    monthHeader = { month ->
                        MonthHeader(month) },
                    dayContent = { day ->
                        Day(day, isSelected = selectedDate == day.date) { day ->
                            selectedDate = if (selectedDate == day.date) null else day.date
                        }
                    }
                )
            }
        }
    }
}

// Creates the days
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Day(day: CalendarDay,
        isSelected: Boolean,
        onClick: (CalendarDay) -> Unit
) {
    Box(
        modifier = Modifier
            .padding(1.dp)
            .aspectRatio(1f),
        contentAlignment = Alignment.Center,
        )
    {
        if (day.position == DayPosition.MonthDate) {
            Box(
                modifier = Modifier
                    .size(54.dp)
                    .clip(shape = RoundedCornerShape(6.dp))
                    .fillMaxSize()
                    .background(color = if (isSelected) Color.Green else Color.White)
                    .border(
                        color = Color.Gray,
                        width = 1.dp,
                        shape = RoundedCornerShape(6.dp)
                    )
                    .clickable(
                        enabled = day.position == DayPosition.MonthDate,
                        onClick = { onClick(day) }
                    ),
                contentAlignment = Alignment.TopStart,
            ) {
                Text(modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp),
                    fontSize = 14.sp,
                    text = day.date.dayOfMonth.toString()
                )
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MonthHeader(calendarMonth: CalendarMonth) {
    // The header that is displayed for every month; contains week & month.
    val daysOfWeek = calendarMonth.weekDays.first().map { it.date.dayOfWeek }
    Column(
        modifier = Modifier
            .padding(vertical = 5.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {

        // Month
        Text(
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp,
            text = calendarMonth.yearMonth.displayText()
            text="Calendar Screen Content",
            modifier = Modifier
                .semantics { contentDescription = "Calendar Page" } // keep somewhere for testing
        )

        // Days of Week
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    text = dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault())
                )
            }
        }
    }
}

// Function to display Month with Year
@RequiresApi(Build.VERSION_CODES.O)
fun YearMonth.displayText(short: Boolean = false): String {
    return "${this.month.displayText(short = short)} ${this.year}"
}

// Function to display Month
@RequiresApi(Build.VERSION_CODES.O)
fun Month.displayText(short: Boolean = true): String {
    val style = if (short) TextStyle.SHORT else TextStyle.FULL
    return getDisplayName(style, Locale.ENGLISH)
}


//@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun CalendarScreenPreview() {
    ThePeriodPurseTheme {
        CalendarScreen()
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview(showBackground = true)
@Composable
fun TabsPreview() {
    val tabs = listOf(
        CalendarTabItem.CalendarTab,
        CalendarTabItem.CycleTab,
    )
    val pagerState = rememberPagerState()
    Tabs(tabs = tabs, pagerState = pagerState)
}
