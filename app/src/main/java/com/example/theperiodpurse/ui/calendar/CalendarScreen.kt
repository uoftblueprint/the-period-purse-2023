package com.example.theperiodpurse.ui.calendar


import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.ripple.LocalRippleTheme
import androidx.compose.material.ripple.RippleAlpha
import androidx.compose.material.ripple.RippleTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.R
import com.example.theperiodpurse.Screen
import com.example.theperiodpurse.ui.theme.HeaderColor1
import com.example.theperiodpurse.ui.theme.SelectedColor1
import com.example.theperiodpurse.data.Symptom
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme
import com.google.accompanist.pager.*
import com.kizitonwose.calendar.compose.VerticalCalendar
import com.kizitonwose.calendar.compose.rememberCalendarState
import com.kizitonwose.calendar.core.*
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Month
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.*


val tabModifier = Modifier
    .background(Color.White)
    .fillMaxWidth()

@Composable
fun SymptomTab(
    trackedSymptoms: List<Symptom>,
    activeSymptom: Symptom?,
    onSymptomClick: (Symptom) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        DisplaySymptomTab(
            activeSymptom = activeSymptom,
            expanded = expanded,
            onExpandButtonClick = { expanded = !expanded },
            modifier = tabModifier
        )
        if (expanded) {
            SwitchSymptomTab(
                activeSymptom = activeSymptom,
                symptoms = trackedSymptoms,
                onSymptomOnClicks = trackedSymptoms.map { { onSymptomClick(it) } },
                modifier = tabModifier
            )
        }
    }
}

@Composable
private fun DisplaySymptomTab(
    activeSymptom: Symptom?,
    expanded: Boolean,
    onExpandButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(activeSymptom?.nameId ?: Symptom.FLOW.nameId),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 2.dp)
        )
        Icon(
            painter = painterResource(
                id = activeSymptom?.resourceId ?: Symptom.FLOW.resourceId
            ),
            tint = Color.Black,
            contentDescription = activeSymptom?.name,
            modifier = Modifier
                .padding(end = 0.dp)
                .testTag("Selected Symptom")
        )
        SwitchSymptomButton(
            expanded = expanded,
            onClick = onExpandButtonClick
        )
    }
}

@Composable
private fun SwitchSymptomButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = Color.Gray,
            contentDescription = stringResource(R.string.expand_button_symptoms_content_description)
        )
    }
}

@Composable
private fun SwitchSymptomTab(
    activeSymptom: Symptom?,
    symptoms: List<Symptom>,
    onSymptomOnClicks: List<() -> Unit>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.testTag("Symptom Options")
    ) {
        symptoms.zip(onSymptomOnClicks).forEach { (symptom, onClick) ->
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()

            IconButton(
                onClick = onClick,
                interactionSource = interactionSource
            ) {
                val defaultColor = Color.Black
                val color = if (isPressed) {
                    defaultColor.copy(ContentAlpha.disabled)
                } else if (activeSymptom == symptom) {
                    Color(0xFFBF3428)
                } else {
                    defaultColor
                }
                Icon(
                    painter = painterResource(id = symptom.resourceId),
                    tint = color,
                    contentDescription = stringResource(symptom.nameId)
                )
            }
        }
    }
}

inline fun Modifier.noRippleClickable(crossinline onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(tabs: List<CalendarTabItem>, pagerState: PagerState) {
    // Composable for tabs
    // Tabs are displayed in ordered row by tabs list
    // PagerState allows for navigation/animation between paginated layouts
    val scope = rememberCoroutineScope()
    CompositionLocalProvider(LocalRippleTheme provides NoRippleTheme()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = HeaderColor1,
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(
                        pagerState,
                        tabPositions
                    ),
                    color = SelectedColor1
                )
            }
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = { Text(tab.title) },
                    selected = pagerState.currentPage == index,
                    modifier = Modifier
                        .noRippleClickable {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
    }
}

private class NoRippleTheme : RippleTheme {
    @Composable
    override fun defaultColor() = RippleTheme.defaultRippleColor(Color.Unspecified, true)

    @Composable
    override fun rippleAlpha(): RippleAlpha =
        RippleAlpha(draggedAlpha = 0f, focusedAlpha = 0f, hoveredAlpha = 0f, pressedAlpha = 0f)
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(
    tabs: List<CalendarTabItem>,
    pagerState: PagerState,
    calendarUIState: CalendarUIState,
    navController: NavController
) {
    HorizontalPager(state = pagerState, count = tabs.size) { page ->
        tabs[page].screen(calendarUIState, navController)
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun CalendarScreen(
    navController: NavController,
    calendarViewModel: CalendarViewModel
) {
    // Main calendar screen which allows navigation to cycle page and calendar
    // By default, opens on to the Calendar
    val tabs = listOf(
        CalendarTabItem.CalendarTab,
        CalendarTabItem.CycleTab
    )
    val pagerState = rememberPagerState()
    val calendarUIState by calendarViewModel.uiState.collectAsState()
    ThePeriodPurseTheme {
        Scaffold(topBar = {})
        { padding ->
            Column(
                verticalArrangement = Arrangement.Bottom,
                modifier = Modifier.padding(padding)
            ) {
                Tabs(tabs = tabs, pagerState = pagerState)
                TabsContent(
                    tabs = tabs,
                    pagerState = pagerState,
                    calendarUIState = calendarUIState,
                    navController = navController
                )
            }
        }
    }
}

val previewTrackedSymptoms = Symptom.values().asList()

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarScreenLayout(calendarUIState: CalendarUIState, navController: NavController) {
    // Contains the swappable content
    ThePeriodPurseTheme {
        val bg = painterResource(R.drawable.colourwatercolour)

        var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
        var activeSymptom: Symptom? by remember { mutableStateOf(null) }
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
            Image(
                painter = bg,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .semantics { contentDescription = "Calendar Page" },
                contentScale = ContentScale.FillBounds,
            )
            Column {
                SymptomTab(
                    trackedSymptoms = previewTrackedSymptoms,
                    activeSymptom = activeSymptom,
                    onSymptomClick = { activeSymptom = it }
//                trackedSymptoms = userDAO.get().symptomsToTrack
                )
                VerticalCalendar(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .semantics { contentDescription = "Calendar" },
                    state = state,
                    monthHeader = { month ->
                        MonthHeader(month)
                    },
                    dayContent = { day ->
                        (if (activeSymptom == null) Symptom.FLOW else activeSymptom)?.let {
                            Day(
                                day = day,
                                calendarDayUIState = calendarUIState.days[day.date],
                                isSelected = selectedDate == day.date,
                                activeSymptom = it
                            ) { date ->
                                selectedDate = if (selectedDate == date.date) null
                                else date.date
                                navController.navigate(
                                    route = "%s/%s/%s"
                                        .format(
                                            Screen.Calendar,
                                            Screen.Log,
                                            day.date.toString()
                                        )
                                )
                            }
                        }
                    }
                )
            }
        }
    }
}

private fun getDayColorAndIcon(
    day: CalendarDay,
    activeSymptom: Symptom,
    calendarDayUIState: CalendarDayUIState?
): Pair<Color, Int> {
    val default = Pair(Color.White, R.drawable.blank)
    if (day.date.isAfter(LocalDate.now())) {
        return Pair(Color(237, 237, 237), R.drawable.blank)
    }
    if (calendarDayUIState == null) {
        return default
    }
    return when (activeSymptom) {
        Symptom.FLOW ->
            when (calendarDayUIState.flow) {
                "Light" -> Pair(Color(215, 125, 125), R.drawable.water_drop_black_24dp)
                "Medium" -> Pair(Color(210, 80, 75), R.drawable.opacity_black_24dp)
                "Heavy" -> Pair(Color(195, 50, 50), R.drawable.flow_heavy)
                "Spotting" -> Pair(Color(245, 192, 192), R.drawable.spotting)
                else -> default
            }
        Symptom.CRAMPS -> default
        Symptom.EXERCISE -> default
        Symptom.MOOD -> default
        Symptom.SLEEP -> default
    }
}
// Creates the days
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Day(
    day: CalendarDay,
    calendarDayUIState: CalendarDayUIState?,
    isSelected: Boolean,
    activeSymptom: Symptom,
    onClick: (CalendarDay) -> Unit,
) {
    val (dayColor, iconId) = getDayColorAndIcon(day, activeSymptom, calendarDayUIState)
    Box(
        modifier = Modifier
            .padding(1.dp)
            .aspectRatio(1f)
    )
    {
        if (day.position == DayPosition.MonthDate) {
            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .fillMaxSize()
                    .background(dayColor)
                    .semantics { contentDescription = day.date.toString() }
                    .border(
                        color = Color(200, 205, 205),
                        width = 1.dp,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .clickable(
                        enabled = !day.date.isAfter(LocalDate.now()),
                        onClick = {
                            if (!day.date.isAfter(LocalDate.now()))
                                onClick(day)
                        }
                    ),
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    text = day.date.dayOfMonth.toString(),
                    color = if (day.date.isAfter(LocalDate.now())) {
                        Color(190, 190, 190)
                    } else {
                        Color.Black
                    }
                )

                Box(modifier = Modifier.padding(12.dp)
                    .align(Alignment.BottomCenter)) {
                    if (calendarDayUIState != null) {
                        /* TODO: Update day box according to DayUIState
                            example: */
                        Image(
                            modifier = Modifier
                                .size(20.dp)
                                .offset(y = 6.dp),
                            painter = painterResource(id = iconId),
                            contentDescription = "DateFlowIcon"
                        )

                    }
                }
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
            .padding(vertical = 18.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Month
        Text(
            modifier = Modifier.padding(12.dp),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.W500,
            text = calendarMonth.yearMonth.displayText()
        )

        // Days of Week
        Row(modifier = Modifier.fillMaxWidth()) {
            for (dayOfWeek in daysOfWeek) {
                Text(
                    modifier = Modifier
                        .weight(1f)
                        .offset(y = 10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.ExtraBold,
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
        CalendarScreen(rememberNavController(), viewModel())
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

@Preview
@Composable
fun DisplaySymptomTabPreview() {
    SymptomTab(trackedSymptoms = previewTrackedSymptoms, Symptom.FLOW, {})
}
