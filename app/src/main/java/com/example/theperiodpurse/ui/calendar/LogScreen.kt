package com.example.theperiodpurse.ui.calendar

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.theperiodpurse.Screen
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.calendar.LogPrompt.Flow.iconTint
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


@Composable
fun LogScreen(
    date: String="0000-00-00",
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
        LogScreenLayout(day, navController, logPrompts)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun LogScreenLayout(
    date: LocalDate,
    navController: NavController,
    logPrompts: List<LogPrompt>
) {
    Column() {
        LogScreenTopBar(
            navController = navController,
            date = date
        )
        LogPromptCards(logPrompts = logPrompts)
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
fun ChangeableExpandButton( expanded: Boolean, onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = 
                if (expanded) Icons.Filled.KeyboardArrowUp
                else Icons.Filled.KeyboardArrowDown,
            tint = Color.Black,
            contentDescription = "Expand Button",
        )
    }
}

@Composable
fun LogPromptCards(logPrompts: List<LogPrompt>) {
    LazyColumn(modifier = Modifier.background(Color.White)) {
        items(logPrompts) {
            Column(
                modifier = Modifier
                    .drawBehind {
                        val strokeWidth = 4f
                        val x = size.width - strokeWidth
                        val y = size.height - strokeWidth

                        drawLine(
                            color = Color(200, 200, 200),
                            start = Offset(0f, 0f), //(0,0) at top-left point of the box
                            end = Offset(x, 0f), //top-right point of the box
                            strokeWidth = strokeWidth
                        )
                    }) {
                LogPromptCard(logPrompt = it)
            }
        }
    }
}

@Composable
fun LogPromptCard(logPrompt: LogPrompt) {
    var expanded by remember { mutableStateOf(false) }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp, bottom = 10.dp, start = 20.dp, end = 20.dp)
        ) {
            logPrompt.icon()
            Spacer(Modifier.size(10.dp))
            Text(logPrompt.title)
            Spacer(Modifier.weight(1f))
            ChangeableExpandButton(expanded = expanded) {
                expanded = !expanded
            }
        }
        if (expanded) {
            logPrompt.prompt()
        }
    }
}

typealias ComposableFun = @Composable () -> Unit
typealias ComposableIconFun = @Composable () -> Unit

open class LogPrompt(var title: String, var icon: ComposableIconFun, var prompt: ComposableFun) {
    val iconTint = Color(50,50,50)
    object Flow : LogPrompt(
        title = "Flow",
        icon = { Icon(
            painter = painterResource(id = R.drawable.opacity_black_24dp),
            contentDescription = "Flow Icon",
            tint = iconTint
        ) },
        prompt = {
            Text("Some Test Text")
        }
    )
    object Mood : LogPrompt(
        title = "Mood",
        icon = { Icon(
            painter = painterResource(id = R.drawable.sentiment_very_satisfied_black_24dp),
            contentDescription = "Mood Icon",
            tint = iconTint
        ) },
        prompt = {}
    )
    object Sleep : LogPrompt(
        title = "Sleep",
        icon = { Icon(
            painter = painterResource(id = R.drawable.nightlight_black_24dp),
            contentDescription = "Sleep Icon",
            tint = iconTint
        ) },
        prompt = {}
    )
    object Cramps : LogPrompt(
        title = "Cramps",
        icon = { Icon(
            painter = painterResource(id = R.drawable.sentiment_very_dissatisfied_black_24dp),
            contentDescription = "Cramp Icon",
            tint = iconTint
        ) },
        prompt = {}
    )
    object Exercise : LogPrompt(
        title = "Exercise",
        icon = { Icon(
            painter = painterResource(id =R.drawable.self_improvement_black_24dp),
            contentDescription = "Exercise Icon",
            tint = iconTint
        ) },
        prompt = {}
    )
    object Notes : LogPrompt(
        title = "Notes",
        icon = { Icon(
            painter = painterResource(id = R.drawable.edit_black_24dp),
            contentDescription = "Notes Icon",
            tint = iconTint
        ) },
        prompt = {}
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
    LogPromptCard(logPrompt = LogPrompt.Flow)
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
    LogPromptCards(logPrompts = logPrompts)
}