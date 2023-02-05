package com.example.theperiodpurse.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.OnboardUIState

/**
 * This composable expects [onboardUIState] that represents the onboarding state, [onCancelButtonClicked] lambda
 * that triggers canceling the order and passes the final order to [onSendButtonClicked] lambda
 */
@Composable
fun SummaryScreen(
    onboardUiState: OnboardUIState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    val resources = LocalContext.current.resources

    val numberOfDays = resources.getQuantityString(
        R.plurals.period_length,
        onboardUiState.days,
        onboardUiState.days,
    )

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,

    ) {

        Spacer(modifier = Modifier.height(100.dp))


        Image(
            painter = painterResource(R.drawable.pad_3_2x),
            contentDescription = null,
            modifier = Modifier.size(200 .dp),
        )

        Text(text ="You're all set!", style = MaterialTheme.typography.h4, fontWeight = FontWeight.Bold)




        Spacer(modifier = Modifier.height(100.dp))



        Text(stringResource(R.string.average_period_length).uppercase())
        Text(text = numberOfDays, fontWeight = FontWeight.Bold)
        Divider(thickness = 1.dp)

        Text(stringResource(R.string.last_period).uppercase())
        Text(text = onboardUiState.date, fontWeight = FontWeight.Bold)
        Divider(thickness = 1.dp)

        Text(stringResource(R.string.symptoms_to_log).uppercase())

        onboardUiState.symptomsOptions.forEach { item ->
            Text(text = item, fontWeight = FontWeight.Bold)
        }
        Divider(thickness = 1.dp)

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSendButtonClicked() },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))

        ) {
            Text(stringResource(R.string.lets_go),color = Color.White, fontSize = 25.sp)
        }

    }
}

@Preview
@Composable
fun OrderSummaryPreview() {
    SummaryScreen(
        onboardUiState = OnboardUIState(5, "June 1st", listOf("test1", "test2")),
        onSendButtonClicked = { },
        onCancelButtonClicked = {}
    )
}
