package com.tpp.theperiodpurse.ui.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.OnboardUIState

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
    ) {

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

        OutlinedButton(
            modifier = Modifier.fillMaxWidth(),
            onClick = onCancelButtonClicked
        ) {
            Text(stringResource(R.string.cancel))
        }

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = { onSendButtonClicked() }
        ) {
            Text(stringResource(R.string.lets_go))
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
