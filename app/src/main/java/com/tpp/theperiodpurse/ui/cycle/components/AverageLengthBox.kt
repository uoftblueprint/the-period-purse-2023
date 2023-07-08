package com.tpp.theperiodpurse.ui.cycle.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.ui.onboarding.scaledSp

@Composable
fun AverageLengthBox(
    modifier: Modifier = Modifier,
    title: String,
    image: Painter,
    length: Float,
    color: Color,
) {
    Card(
        modifier.width(177.dp),
        elevation = 2.dp,
        backgroundColor = color,
        shape = RoundedCornerShape(10),
    ) {
        Column(modifier.padding(18.dp)) {
            Text(
                text = title,
                fontSize = 12.scaledSp(),
                fontWeight = FontWeight(700),
                color = Color(0xFF868083),
            )
            Spacer(modifier.height(20.dp))
            AverageLengthRow(length, modifier, image)
        }
    }
}

@Composable
private fun AverageLengthRow(
    length: Float,
    modifier: Modifier,
    image: Painter,
) {
    Row {
        Text(
            text = when (length) {
                (-1).toFloat() -> stringResource(R.string.log_to_learn)
                (-2).toFloat() -> stringResource(R.string.log_to_learn)
                else -> "%.2f Days".format(length)
            },
            fontSize = when (length) {
                (-1).toFloat() -> 10.scaledSp()
                (-2).toFloat() -> 10.scaledSp()
                else -> 20.scaledSp()
            },
            fontWeight = FontWeight(500),
            modifier = modifier.width(55.dp),
        )
        Spacer(modifier.width(29.dp))
        Box(
            modifier
                .size(50.dp)
                .clip(RoundedCornerShape(50))
                .background(Color.White),
        ) {
            Image(
                painter = image,
                contentDescription = null,
                modifier = Modifier
                    .matchParentSize()
                    .aspectRatio(1f) // Maintain a 1:1 aspect ratio
                    .padding(8.dp), // Add padding to shrink the image inside the box
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
            )
        }
    }
}
