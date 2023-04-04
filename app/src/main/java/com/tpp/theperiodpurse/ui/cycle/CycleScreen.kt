package com.tpp.theperiodpurse.ui.cycle

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpp.theperiodpurse.AppViewModel
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.*
import com.tpp.theperiodpurse.ui.theme.ThePeriodPurseTheme

private var periodLength = (-1).toFloat()
private var cycleLength = (-1).toFloat()


@Composable
fun CurrentCycleBox(modifier: Modifier = Modifier, dates: ArrayList<Date>) {
    Card(
        modifier
            .fillMaxWidth()
            .height(300.dp), elevation = 2.dp, shape = RoundedCornerShape(5)
    ) {
        Text(
            text = stringResource(R.string.current_cycle),
            fontSize = 20.sp,
            fontWeight = FontWeight(700),
            color = Color(0xFFB12126),
            modifier = modifier.padding(start = 20.dp, top = 20.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .size(200.dp)
            ) {
                Canvas(modifier = Modifier.matchParentSize()) {
                    val strokeWidth = 25.dp.toPx()
                    val ringColor = Color(0xFFB12126)
                    val radius = (size.minDimension - strokeWidth) / 2

                    drawArc(
                        color = ringColor.copy(alpha = 0.2f),
                        startAngle = 0f,
                        sweepAngle = 360f,
                        useCenter = false,
                        topLeft = center - Offset(radius, radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = strokeWidth)
                    )

                    // Draw the progress ring
                    drawArc(
                        color = ringColor,
                        startAngle = -90f,
                        sweepAngle = calculateArcAngle(dates),
                        useCenter = false,
                        topLeft = center - Offset(radius, radius),
                        size = Size(radius * 2, radius * 2),
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }
                Column(
                    modifier = modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = calculateDaysSinceLastPeriod(dates).toString(),
                        fontSize = 50.sp,
                        fontWeight = FontWeight(900),
                        color = Color(0xFFB12126)
                    )
                    Text(
                        text = "Days since",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF868083),
                    )
                    Text(
                        text = "last period",
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                        color = Color(0xFF868083),
                    )
                }
            }
        }
    }
}

@Composable
fun AverageLengthBox(
    modifier: Modifier = Modifier,
    title: String,
    image: Painter,
    length: Float,
    color: Color
) {
    Card(
        modifier.width(177.dp),
        elevation = 2.dp,
        backgroundColor = color,
        shape = RoundedCornerShape(10)
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
                    text = when (length) {
                        (-1).toFloat() -> stringResource(R.string.log_to_learn)
                        (-2).toFloat() -> stringResource(R.string.log_to_learn)
                        else -> "$length Days"
                    },
                    fontSize = when (length) {
                        (-1).toFloat() -> 10.sp
                        (-2).toFloat() -> 10.sp
                        else -> 20.sp
                    },
                    fontWeight = FontWeight(500),
                    modifier = modifier.width(55.dp)
                )
                Spacer(modifier.width(29.dp))
                Box(
                    modifier
                        .size(50.dp)
                        .clip(RoundedCornerShape(50))
                        .background(Color.White)
                ) {
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier
                            .matchParentSize()
                            .aspectRatio(1f) // Maintain a 1:1 aspect ratio
                            .padding(8.dp), // Add padding to shrink the image inside the box
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center
                    )
                }
            }
        }
    }
}

@Composable
fun CycleHistoryBox(modifier: Modifier = Modifier) {
    Card(
        modifier.fillMaxWidth(), elevation = 2.dp, shape = RoundedCornerShape(10)
    ) {
        Column(modifier.padding(horizontal = 30.dp, vertical = 25.dp)) {
            Text(
                text = stringResource(R.string.cycle_history),
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                color = Color(0xFF868083),
            )
            Divider(
                color = Color(0xFF868083),
                modifier = modifier
                    .padding(vertical = 15.dp)
                    .fillMaxWidth()
            )
            Text(text = "TODO")
        }
    }
}

@Composable
fun CycleScreenLayout(
    appViewModel: AppViewModel,
    modifier: Modifier = Modifier
) {
    val dates = ArrayList(appViewModel.getDates())

    periodLength = calculateAveragePeriodLength(dates)
    cycleLength = calculateAverageCycleLength(dates)

    ThePeriodPurseTheme {
        val bg = painterResource(R.drawable.colourwatercolour)
        Box {
            Image(
                painter = bg,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .semantics { contentDescription = "Cycle Page" },
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight()
                    .padding(
                        horizontal = 20.dp, vertical = 25.dp
                    )
            ) {
                CurrentCycleBox(dates = dates)
                Spacer(modifier.height(30.dp))
                Row {
                    AverageLengthBox(
                        title = stringResource(R.string.avg_period_len),
                        color = Color(0xFFFEDBDB),
                        length = periodLength,
                        image = painterResource(R.drawable.flow_with_heart)
                    )
                    Spacer(modifier.width(16.dp))
                    AverageLengthBox(
                        title = stringResource(R.string.avg_cycle_len),
                        color = Color(0xFFBAE0D8),
                        length = cycleLength,
                        image = painterResource(R.drawable.menstruation_calendar__1_)
                    )
                }
                Spacer(modifier.height(30.dp))
                CycleHistoryBox()
            }
        }
    }
}