package com.tpp.theperiodpurse.ui.cycle.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.calculateArcAngle
import com.tpp.theperiodpurse.data.calculateDaysSinceLastPeriod
import com.tpp.theperiodpurse.data.entity.Date
import com.tpp.theperiodpurse.ui.onboarding.scaledSp

@Composable
fun CurrentCycleBox(modifier: Modifier = Modifier, dates: ArrayList<Date>) {
    Card(
        modifier
            .fillMaxWidth()
            .height(300.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(5)
    ) {
        Text(
            text = stringResource(R.string.current_cycle),
            fontSize = 20.scaledSp(),
            fontWeight = FontWeight(700),
            color = Color(0xFFB12126),
            modifier = modifier.padding(start = 20.dp, top = 20.dp)
        )
        Spacer(
            modifier = Modifier.height(16.dp)
        )
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
                        fontSize = 50.scaledSp(),
                        fontWeight = FontWeight(900),
                        color = Color(0xFFB12126)
                    )
                    Text(
                        text = "Days since",
                        fontSize = 16.scaledSp(),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF868083),
                    )
                    Text(
                        text = "last period",
                        fontSize = 16.scaledSp(),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF868083),
                    )
                }
            }
        }
    }
}
