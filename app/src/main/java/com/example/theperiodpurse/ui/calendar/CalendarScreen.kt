package com.example.theperiodpurse.ui.calendar

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun CalendarCycleTopBar () {
    Row(
        modifier = Modifier
        .fillMaxWidth()
    ) {
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = "Calendar",
                fontSize = 24.sp,
                modifier = Modifier
                    .weight(.5f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, bottom = 16.dp)
            )
        }
        OutlinedButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .weight(1f)
        ) {
            Text(text = "Cycle",
                fontSize = 24.sp,
                modifier = Modifier
                    .weight(.5f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .padding(top = 16.dp, bottom = 16.dp)
            )
        }
    }
}

@Composable
fun CalendarScreenLayout() {

}

@Preview
@Composable
fun CalendarScreenPreview() {
    CalendarScreen()
}

@Preview
@Composable
fun CalendarCycleTopBarPreview () {
    CalendarCycleTopBar()
}