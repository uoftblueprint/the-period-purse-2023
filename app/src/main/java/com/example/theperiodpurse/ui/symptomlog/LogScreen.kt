package com.example.theperiodpurse.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.Screen
import com.example.theperiodpurse.data.LogPrompt
import com.example.theperiodpurse.data.LogSquare
import com.example.theperiodpurse.ui.symptomlog.LogViewModel
import com.example.theperiodpurse.ui.theme.HeaderColor1
import com.example.theperiodpurse.ui.theme.SecondaryFontColor
import com.example.theperiodpurse.ui.theme.SelectedColor1
import com.kizitonwose.calendar.core.atStartOfMonth
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun LogScreen(
    date: String="0001-01-01",
    calendarViewModel: CalendarViewModel,
    navController: NavController
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val day = LocalDate.parse(date)

        val logPrompts = listOf(
            LogPrompt.Flow,
            LogPrompt.Mood,
            LogPrompt.Sleep,
            LogPrompt.Cramps,
            LogPrompt.Exercise,
            LogPrompt.Notes
        )

        val logSquarePrompts = listOf( // log prompts that use selectable squares
            LogPrompt.Flow
        )

        val logViewModel = LogViewModel(logSquarePrompts)
        val calendarState by calendarViewModel.uiState.collectAsState()
        val dayUIState = calendarState.days[day]
        if (dayUIState != null) {
            logViewModel.populateWithUIState(dayUIState)
        }

        LogScreenLayout(
            day, navController, logPrompts, logViewModel,
            onSave = {
                calendarViewModel.saveDayInfo(
                    day,
                    CalendarDayUIState(
                        flow = logViewModel.getSelectedFlow(),
                        mood = logViewModel.getSelectedMood(),
                        exerciseLengthString = logViewModel.getText(LogPrompt.Exercise),
                        exerciseType = logViewModel.getSelectedExercise(),
                        crampSeverity = logViewModel.getSelectedCrampSeverity(),
                        sleepString = logViewModel.getText(LogPrompt.Sleep)
                    )
                )
                navController.navigateUp()
            })
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogScreenLayout(
    date: LocalDate,
    navController: NavController,
    logPrompts: List<LogPrompt>,
    logViewModel: LogViewModel,
    onSave: () -> Unit,
) {
    Box() {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            LogScreenTopBar(
                navController = navController,
                date = date
            )
            LogPromptCards(logPrompts = logPrompts, logViewModel = logViewModel)
            Spacer(modifier = Modifier
                .height(130.dp)
                .weight(1f))
        }
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(modifier = Modifier
                .weight(1f)
            )

            SaveButton(onClick = onSave)
        }

    }


}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogScreenTopBar(navController: NavController, date: LocalDate) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = HeaderColor1)
    ) {
        Row (modifier = Modifier
            .padding(start = 10.dp, top = 10.dp, end = 0.dp)
        ) {
            IconButton(
                onClick = { navController.navigateUp() },
                modifier = Modifier
                    .then(Modifier.size(24.dp))
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "Log Close Button"
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 0.dp, bottom = 0.dp, start = 35.dp, end = 35.dp)
                .height(65.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(.1f),
            ) {
                if (date.minusDays(1) >=
                    YearMonth.now().minusMonths(12).atStartOfMonth())
                    IconButton(onClick = {
                    navController.navigate(
                        "%s/%s/%s".format(
                            Screen.Calendar.name,
                            Screen.Log.name,
                            date.minusDays(1).toString()
                        )
                    ) {
                        popUpTo(Screen.Calendar.name) {
                            inclusive = false
                        }
                    } },
                    modifier = Modifier
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Log Back Arrow"
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(.8f)
            ) {
                Text(
                    text = "Log your symptoms for:",
                    color = Color(50,50,50),
                    fontSize = 12.sp
                )
                Text(
                    text = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL)),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .testTag("DateLabel")
                        .semantics { contentDescription = "DateLabel" }
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(.1f)
            ) {
                if (date.plusDays(1)<=LocalDate.now())
                    IconButton(onClick = {
                        navController.navigate("%s/%s/%s"
                            .format(
                                Screen.Calendar.name,
                                Screen.Log.name,
                                date.plusDays(1)
                            )
                        ) {
                            popUpTo(Screen.Calendar.name) {
                                inclusive = false
                            }
                        }
                },
                modifier = Modifier
                    .semantics { contentDescription = "ClickNextDay" }
                    ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = "Log Forward Arrow"
                    )
                }
            }

        }
    }
}

@Composable
fun LogPromptCards(logPrompts: List<LogPrompt>, logViewModel: LogViewModel) {
    LazyColumn(modifier = Modifier.background(Color.White)) {
        items(logPrompts) {
            Column(
                modifier = Modifier
                    .drawBehind {
                        val strokeWidth = 4f
                        val x = size.width - strokeWidth

                        drawLine(
                            color = HeaderColor1,
                            start = Offset(0f, 0f), //(0,0) at top-left point of the box
                            end = Offset(x, 0f), //top-right point of the box
                            strokeWidth = strokeWidth
                        )
                    }) {
                LogPromptCard(logPrompt = it, logViewModel)
            }
        }
    }
}

@Composable
fun LogPromptCard(logPrompt: LogPrompt, logViewModel: LogViewModel) {
    var expanded by remember { mutableStateOf(false) }

    val tabColor =  animateColorAsState(
        targetValue =
        if (logViewModel.getSquareSelected(logPrompt) != null ||
            logViewModel.getText(logPrompt) != "") SelectedColor1
        else SecondaryFontColor,
        animationSpec = tween(500, 0, LinearEasing)
    )

    Column(
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column (
            modifier = Modifier
                .clickable { expanded = !expanded }
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 15.dp)
            ) {
                logPrompt.icon(tabColor.value)
                Spacer(Modifier.size(10.dp))
                Text(
                    text = logPrompt.title,
                    fontWeight = FontWeight.Bold,
                    color = tabColor.value,
                    fontSize = 16.sp
                )
                Spacer(Modifier.weight(1f))
                ChangeableExpandButton(expanded = expanded) {
                    expanded = !expanded
                }
            }
        }
        if (expanded) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(
                        top = 10.dp,
                        bottom = 20.dp,
                        start = 40.dp,
                        end = 40.dp
                    )
            ) {
                logPrompt.prompt(logViewModel)
            }
        }
    }
}

@Composable
fun ChangeableExpandButton( expanded: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector =
            if (expanded) Icons.Filled.KeyboardArrowUp
            else Icons.Filled.KeyboardArrowDown,
            tint = SelectedColor1,
            contentDescription = "Expand Button",
        )
    }
}

@Composable
fun LogSelectableSquare(
    logSquare: LogSquare,
    selected : String?,
    onClick: (LogSquare) -> Unit
) {

    val squareColor = animateColorAsState(
        targetValue =
            if (logSquare.description == selected) SelectedColor1
            else HeaderColor1,
        animationSpec = tween(250, 0, LinearEasing)
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .wrapContentSize(Alignment.Center)
    ) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(75.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(
                    color = squareColor.value
                )
                .clickable {
                    onClick(logSquare)
                }
                .semantics { contentDescription = logSquare.description }
        ) {
            logSquare.icon(SecondaryFontColor)
        }
        Text(
            text = logSquare.description,
            fontSize = 16.sp,
            color = SecondaryFontColor,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp, bottom = 20.dp)
        )
    }
}

@Composable
fun SaveButton(
    onClick: () -> Unit
) {
    Column (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
    ) {
        FloatingActionButton(
            onClick = onClick,
            backgroundColor = SelectedColor1,
            modifier = Modifier
                .padding(20.dp)
                .width(350.dp)
                .height(40.dp)
        ) {
            Text(
                text = "Save",
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .semantics { contentDescription = "Save Button" },
                color = Color.White
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun LogScreenLayoutPreview() {
    val logPrompts = listOf(
        LogPrompt.Flow,
        LogPrompt.Mood,
        LogPrompt.Sleep,
        LogPrompt.Cramps,
        LogPrompt.Exercise,
        LogPrompt.Notes
    )
    LogScreenLayout(
        date = LocalDate.parse("2022-12-07"),
        navController = rememberNavController(),
        logPrompts = logPrompts,
        logViewModel = LogViewModel(logPrompts),
        onSave = {}
    )
}

@Preview
@Composable
fun LogScreenTopBarPreview() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        LogScreenTopBar(
            date = LocalDate.parse("2000-01-01"),
            navController = rememberNavController()
        )
    }
}

@Preview
@Composable
fun LogPromptCardPreview() {
    val logPrompts = listOf(LogPrompt.Flow)
    val logViewModel = LogViewModel(logPrompts)

    LogPromptCard(logPrompt = LogPrompt.Flow, logViewModel)
}

@Preview
@Composable
fun LogPromptCardsPreview() {
    val logPrompts = listOf(
        LogPrompt.Flow,
        LogPrompt.Mood,
        LogPrompt.Sleep,
        LogPrompt.Cramps,
        LogPrompt.Exercise,
        LogPrompt.Notes
    )
    val logViewModel = LogViewModel(logPrompts)
    LogPromptCards(logPrompts = logPrompts, logViewModel)
}

@Preview
@Composable
fun LogSelectableSquarePreview() {
    val logPrompts = listOf(LogPrompt.Flow)
    val logViewModel = LogViewModel(logPrompts)
    var selected by remember { mutableStateOf<String?>(null) }

    LogSelectableSquare(
        logSquare = LogSquare.FlowLight,
        selected = selected
    ) { logSquare ->
        if (selected == logSquare.description) {
            selected = null
            logViewModel.resetSquareSelected(logSquare)
        } else {
            selected = logSquare.description
            logViewModel.setSquareSelected(logSquare)
        }
    }
}

@Preview
@Composable
fun SaveButtonPreview() {
    SaveButton {}
}