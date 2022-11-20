/*
 * Copyright (C) 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.theperiodpurse.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.OnboardUIState
import com.example.theperiodpurse.ui.component.FormattedPriceLabel

/**
 * This composable expects [orderUiState] that represents the order state, [onCancelButtonClicked] lambda
 * that triggers canceling the order and passes the final order to [onSendButtonClicked] lambda
 */
@Composable
fun SummaryScreen(
    onboardUiState: OnboardUIState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
){
    val resources = LocalContext.current.resources

    val numberOfDays = resources.getQuantityString(
        R.plurals.period_length,
        onboardUiState.days,
        onboardUiState.days,
    )
    //Load and format a string resource with the parameters.
    val orderSummary = stringResource(
        R.string.order_details,
        numberOfDays,
        onboardUiState.symptomsOptions,
        onboardUiState.date,
        onboardUiState.days,

    )
    val newAccount = stringResource(R.string.new_account)
    //Create a list of order summary to display

    Column (
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
            Text(stringResource(R.string.next))
        }

    }
}

@Preview
@Composable
fun OrderSummaryPreview(){
    SummaryScreen(
        onboardUiState = OnboardUIState(5, "June 1st", listOf("test1", "test2")),
        onSendButtonClicked = {  },
        onCancelButtonClicked = {}
    )
}
