package com.example.theperiodpurse.ui.calendar

import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme

class CalendarScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThePeriodPurseTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CalendarScreenLayout()
                }
            }
        }
    }
}

@Composable
fun CalendarCycleTopBar (isCalendar: Boolean) {
    var calendarTextColor = Color.DarkGray
    var cycleTextColor = Color.Black
    if (isCalendar) {
        calendarTextColor = Color.Black
        cycleTextColor = Color.DarkGray
    }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                border = BorderStroke(0.dp, color = Color.LightGray),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Calendar",
                    fontSize = 24.sp,
                    color = calendarTextColor,
                    modifier = Modifier
                        .weight(.5f)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(top = 16.dp, bottom = 6.dp)
                )
            }
            Button(
                onClick = { /*TODO*/ },
                shape = RectangleShape,
                border = BorderStroke(0.dp, color = Color.LightGray),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.LightGray),
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(text = "Cycle",
                    fontSize = 24.sp,
                    color = cycleTextColor,
                    modifier = Modifier
                        .weight(.5f)
                        .wrapContentWidth(Alignment.CenterHorizontally)
                        .padding(top = 16.dp, bottom = 6.dp)
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier
                .weight(.5f)
                .size(1.dp)
                .clip(RectangleShape)
                .background(color = calendarTextColor)
            )
            Box(modifier = Modifier
                .weight(.5f)
                .size(5.dp)
                .clip(RectangleShape)
                .background(color = Color.LightGray)
            )
        }
    }

}

fun CalendarBottomBar () {

}

@Composable
fun CalendarScreenLayout() {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        CalendarCycleTopBar(true)
    }
}

@Preview
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}

@Preview
@Composable
fun CalendarCycleTopBarPreview () {
    CalendarCycleTopBar(true)
}