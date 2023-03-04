package com.example.theperiodpurse.ui

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
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.Date
import com.example.theperiodpurse.data.DateConverter
import com.example.theperiodpurse.data.OnboardUIState
import com.example.theperiodpurse.data.Symptom
import com.example.theperiodpurse.ui.onboarding.OnboardViewModel
import com.example.theperiodpurse.ui.onboarding.setDateTo
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField
import java.time.temporal.ChronoUnit

/**
 * This composable expects [onboardUIState] that represents the onboarding state, [onCancelButtonClicked] lambda
 * that triggers canceling the order and passes the final order to [onSendButtonClicked] lambda
 */
@Composable
fun SummaryScreen(
    onboardUiState: OnboardUIState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: OnboardViewModel,
) {
    val resources = LocalContext.current.resources

    val viewModel: OnboardViewModel = viewModel

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
            onClick = {
//                viewModel.addNewUser(getSymptomsToTrack(onboardUiState.symptomsOptions),
//                    arrayListOf<Date>(),
//                    onboardUiState.days,
//                    0,
//                    getDaysSince(onboardUiState.date)
//                )

                onSendButtonClicked() }
        ) {
            Text(stringResource(R.string.lets_go))
        }

    }
}

fun getDaysSince(date: String): Int {

    val dateFormatter: DateTimeFormatter =  DateTimeFormatter.ofPattern("MM/dd/yyyy")
    var date= date.split("|")[0]

    val from = LocalDate.parse(date, dateFormatter)

    val today = LocalDate.now()

    return ChronoUnit.DAYS.between(today, from).toInt()

}

fun getSymptomsToTrack(strList: List<String>): ArrayList<Symptom> {

    val list_so_far = arrayListOf<Symptom>()

    strList.forEach {
        symptom ->
        when (symptom) {
            "Mood" -> {list_so_far.add(Symptom.MOOD)}
            "Exercise" -> {list_so_far.add(Symptom.EXERCISE)}
            "Flow" -> {list_so_far.add(Symptom.FLOW)}
            "Cramps" -> {list_so_far.add(Symptom.CRAMPS)}
            "Sleep" -> {list_so_far.add(Symptom.SLEEP)}
        }

    }
    return list_so_far

}

