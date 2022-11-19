package com.example.theperiodpurse.ui.cycle

import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme

class CycleScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ThePeriodPurseTheme {
                Surface(color = MaterialTheme.colors.background) {
                    CycleScreenLayout()
                }
            }
        }
    }
}

@Composable
fun CurrentCycleBox() {
    Card(
        Modifier
            .fillMaxWidth()
            .height(300.dp),
        elevation = 2.dp, shape = RoundedCornerShape(5)
    ) {
        Text(
            text = "Current cycle",
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
            color = Color(0xFFB12126),
            modifier = Modifier.padding(start = 20.dp, top = 20.dp)
        )

    }
}

@Composable
fun AverageLengthBox(title: String, image: String = "Change this into Image later", color: Color) {
    Card(Modifier.width(166.dp), elevation = 2.dp,
        backgroundColor = color, shape = RoundedCornerShape(10)
    ) {
        Column(Modifier.padding(18.dp)) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF868083),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                Text(
                    text = "Please start logging to learn more.",
                    fontSize = 10.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier.width(55.dp)
                )
                Spacer(modifier = Modifier.width(29.dp))
                Box(
                    Modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.Red)) {}
            }
        }
    }
}

@Composable
fun CycleHistoryBox() {
    Card(Modifier.fillMaxWidth(), elevation = 2.dp, shape =
    RoundedCornerShape(10)) {
        Column(Modifier.padding(horizontal = 30.dp, vertical = 25.dp)) {
            Text(
                text = "Cycle history",
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF868083),
            )
            Divider(color = Color(0xFF868083), modifier = Modifier
                .padding(vertical = 15.dp).fillMaxWidth())
            Text(text = "Please start logging to learn more.")
        }
    }
}

@Composable
fun CycleScreenLayout() {
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                start = 20.dp, end = 20.dp,
                top = 25.dp
            )
    ) {
        CurrentCycleBox()
        Spacer(modifier = Modifier.height(30.dp))
        Row {
            AverageLengthBox(title = "Average period length", color = Color(0xFFFEDBDB))
            Spacer(modifier = Modifier.width(16.dp))
            AverageLengthBox(title = "Average cycle length", color = Color(0xFFBAE0D8))
        }
        Spacer(modifier = Modifier.height(30.dp))
        CycleHistoryBox()
    }
}

@Preview(showBackground = true)
@Composable
fun CycleScreenPreview() {
    ThePeriodPurseTheme {
        CycleScreenLayout()
    }
}