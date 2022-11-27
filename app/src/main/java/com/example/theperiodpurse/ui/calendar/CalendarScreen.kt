package com.example.theperiodpurse.ui.calendar

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch


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
                    tabPositions)
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
    ThePeriodPurseTheme() {
        Scaffold (topBar = {})
        { padding ->
            Column(modifier = Modifier.padding(padding)) {
                Tabs(tabs = tabs, pagerState = pagerState)
                TabsContent(tabs = tabs, pagerState = pagerState)
            }
        }
    }
}

@Composable
fun CalendarScreenLayout() {
    // Contains the swippable content
    ThePeriodPurseTheme() {
        Text(
            text="Calendar Screen Content"
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun CalendarScreenPreview() {
    ThePeriodPurseTheme() {
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
