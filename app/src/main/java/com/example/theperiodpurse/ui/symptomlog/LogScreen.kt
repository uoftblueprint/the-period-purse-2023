package com.example.theperiodpurse.ui.calendar

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.Spring
import com.example.theperiodpurse.ui.theme.Teal
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun LogScreen(
    date: String="0001-01-01",
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

        LogScreenLayout(day, navController, logPrompts, logViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogScreenLayout(
    date: LocalDate,
    navController: NavController,
    logPrompts: List<LogPrompt>,
    logViewModel: LogViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        LogScreenTopBar(
            navController = navController,
            date = date
        )
        LogPromptCards(logPrompts = logPrompts, logViewModel = logViewModel)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogScreenTopBar(navController: NavController, date: LocalDate) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(200, 200, 200))
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
                    .weight(.1f)
            ) {
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
                    textAlign = TextAlign.Center
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .weight(.1f)
            ) {
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
                }) {
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
                            color = Color(200, 200, 200),
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
            logViewModel.getText(logPrompt) != "") Teal
        else Color(50,50,50),
        animationSpec = tween(500, 0, LinearEasing)
    )

    Column (
        modifier = Modifier
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 30.dp, end = 15.dp)
                .clickable { expanded = !expanded }
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
            tint = Color(97, 153, 154),
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
            if (logSquare.description == selected) Teal
            else Color(200,200,200),
        animationSpec = tween(250, 0, LinearEasing)
    )

    Column(
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
        ) {
            logSquare.icon(Color(50, 50, 50))
        }
        Text(
            text = logSquare.description,
            fontSize = 16.sp,
            color = Color(50, 50, 50),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 10.dp, bottom = 20.dp)
        )
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
        logViewModel = LogViewModel(logPrompts)
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