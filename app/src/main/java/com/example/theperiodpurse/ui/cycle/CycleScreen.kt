package com.example.theperiodpurse.ui.cycle

import android.media.Image
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.theme.ThePeriodPurseTheme

@Composable
fun CurrentCycleBox(modifier: Modifier = Modifier) {
    Card(
        modifier
            .fillMaxWidth()
            .height(300.dp),
        elevation = 2.dp, shape = RoundedCornerShape(5)
    ) {
        Text(
            text = stringResource(R.string.current_cycle),
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
            color = Color(0xFFB12126),
            modifier = modifier.padding(start = 20.dp, top = 20.dp)
        )

    }
}

@Composable
fun AverageLengthBox(modifier: Modifier = Modifier, title: String,
                     image: String = "Change this into Image later", color: Color) {
    Card(modifier.width(177.dp), elevation = 2.dp,
        backgroundColor = color, shape = RoundedCornerShape(10)
    ) {
        Column(modifier.padding(18.dp)) {
            Text(
                text = title,
                fontSize = 12.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF868083),
            )
            Spacer(modifier.height(20.dp))
            Row {
                Text(
                    text = stringResource(R.string.log_to_learn),
                    fontSize = 10.sp,
                    fontWeight = FontWeight(500),
                    modifier = modifier.width(55.dp)
                )
                Spacer(modifier.width(29.dp))
                Box(
                    modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.Red)) {}
            }
        }
    }
}

@Composable
fun CycleHistoryBox(modifier: Modifier = Modifier) {
    Card(modifier.fillMaxWidth(), elevation = 2.dp, shape =
    RoundedCornerShape(10)) {
        Column(modifier.padding(horizontal = 30.dp, vertical = 25.dp)) {
            Text(
                text = stringResource(R.string.cycle_history),
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF868083),
            )
            Divider(color = Color(0xFF868083), modifier = modifier
                .padding(vertical = 15.dp).fillMaxWidth())
            Text(text = stringResource(R.string.log_to_learn))
        }
    }
}

@Composable
fun CycleScreenLayout(modifier: Modifier = Modifier) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(
                horizontal = 20.dp,
                vertical = 25.dp
            )
            .semantics { contentDescription = "Cycle Page"}
    ) {
        CurrentCycleBox()
        Spacer(modifier.height(30.dp))
        Row {
            AverageLengthBox(title = stringResource(R.string.avg_period_len),
                color = Color(0xFFFEDBDB))
            Spacer(modifier.width(16.dp))
            AverageLengthBox(title = stringResource(R.string.avg_cycle_len),
                color = Color(0xFFBAE0D8))
        }
        Spacer(modifier.height(30.dp))
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