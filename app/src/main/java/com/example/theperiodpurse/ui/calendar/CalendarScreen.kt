package com.example.theperiodpurse.ui.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.component.FloatingActionButton
import com.example.theperiodpurse.ui.theme.Red
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
                    tabPositions
                ),
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
fun CalendarScreen(modifier: Modifier = Modifier) {
    // Main calendar screen which allows navigation to cycle page and calendar
    // By default, opens on to the Calendar
    val tabs = listOf(
        CalendarTabItem.CalendarTab,
        CalendarTabItem.CycleTab
    )
    val pagerState = rememberPagerState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Red)
    ) {
        Tabs(tabs = tabs, pagerState = pagerState)
        TabsContent(tabs = tabs, pagerState = pagerState)
    }
}

@Composable
fun CalendarScreenLayout(modifier: Modifier = Modifier) {
    // Contains the swippable content
    var visible by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = dimensionResource(R.dimen.fab_bottom_padding))
            .offset(y = 40.dp)
    ) {

        val circle = MaterialTheme.shapes.large.copy(CornerSize(percent = 50))

        FloatingActionButton(
            onClick = { visible = true },
            iconId = R.drawable.add_black_24dp,
            contentDescription = stringResource(R.string.fab_see_log_options),
            backgroundColor = Red,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .border(color = Color.White, width = 2.dp, shape = circle)
        )

        if (visible) {
            SelectLogOptionsOverlay(cancelOnClick = { visible = false })
        }
        ThePeriodPurseTheme() {
            Text(
                text = "Calendar Screen Content",
                modifier = Modifier
                    .semantics {
                        contentDescription = "Calendar Page"
                    } // keep somewhere for testing
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Preview
@Composable
fun CalendarScreenPreview() {
    ThePeriodPurseTheme() {
        CalendarScreen(modifier = Modifier.padding(bottom = 15.dp))
    }
}

@Composable
fun CalendarFloatingActionButton(
    onClick: () -> Unit,
    text: String?,
    iconId: Int,
    contentDescription: String?,
    modifier: Modifier = Modifier
) {
    ConstraintLayout {
        val (button, buttonInfo) = createRefs()
        FloatingActionButton(
            onClick = onClick,
            iconId = iconId,
            contentDescription = contentDescription,
            backgroundColor = Red,
            modifier = modifier.constrainAs(button) {}
        )
        if (text != null) {
            Text(
                text = text,
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Visible,
                modifier = Modifier
                    .constrainAs(buttonInfo) {
                        bottom.linkTo(button.top, margin = 7.dp)
                        centerHorizontallyTo(button)
                        width = Dimension.percent(1.1f)
                    }
            )
        }
    }
}

@Composable
fun SelectLogOptionsOverlay(
    cancelOnClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        color = Color.White.copy(alpha = 0.7f),
        modifier = Modifier
            .fillMaxSize()
            .offset(y = 15.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .offset(y = -15.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom,
            ) {
                CalendarFloatingActionButton(
                    onClick = {},
                    text = "Log daily symptoms",
                    iconId = R.drawable.water_drop_black_24dp,
                    contentDescription = null,
                )

                Spacer(Modifier.width(70.dp))
                CalendarFloatingActionButton(
                    onClick = {},
                    text = "Log multiple period dates",
                    iconId = R.drawable.today_black_24dp,
                    contentDescription = null,
                )
            }
            FloatingActionButton(
                onClick = cancelOnClick,
                iconId = R.drawable.close_black_24dp,
                contentDescription = null,
                backgroundColor = Color.White,
                contentColor = Red,
            )
        }
    }
}


@Preview
@Composable
fun PreviewFloatingActionButton() {
    CalendarScreenLayout()
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
