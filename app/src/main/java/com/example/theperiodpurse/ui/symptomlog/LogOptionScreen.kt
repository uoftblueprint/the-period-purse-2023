package com.example.theperiodpurse.ui.symptomlog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.theme.Red
import com.example.theperiodpurse.ui.theme.Shapes

@Composable
fun LoggingOptionsPopup(
    onLogDailySymptomsClick: () -> Unit,
    onLogMultiplePeriodDates: () -> Unit,
    onExit: () -> Unit,
    modifier: Modifier = Modifier
) {
    Popup(
        alignment = Alignment.Center,
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White.copy(0.5f))
                .then(modifier)
                .offset(y = 35.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                LoggingOptionButton(
                    title = "Log daily symptoms",
                    icon = R.drawable.water_drop_black_24dp,
                    onClick = {
                        onLogDailySymptomsClick()
                        onExit()
                    }
                )
                Spacer(modifier = Modifier.width(20.dp))
                LoggingOptionButton(
                    title = "Log multiple period dates",
                    icon = R.drawable.today_black_24dp,
                    onClick = {
                        onExit()
                        onLogMultiplePeriodDates()
                    }
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            CloseOverlayButton(onClick = onExit)
        }
    }
}

@Composable
fun CloseOverlayButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        onClick = onClick,
        backgroundColor = Color.White,
        modifier = modifier.size(70.dp)
    ) {
        Icon(
            painterResource(R.drawable.close_black_24dp),
            contentDescription = "Close logging options overlay",
            tint = Red
        )
    }
}

@Composable
private fun LoggingOptionButton(
    title: String,
    icon: Int,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.width(80.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        FloatingActionButton(
            onClick = onClick,
            backgroundColor = Red,
            modifier = modifier.size(70.dp)
        ) {
            Icon(painterResource(icon), contentDescription = title, tint = Color.White)
        }
    }
}

@Preview
@Composable
fun LogOptionScreenPreview() {
    LoggingOptionsPopup({}, {}, {})
}