package com.example.theperiodpurse.ui.symptomlog

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.LogPrompt
import com.example.theperiodpurse.ui.onboarding.EditNumberField
import java.sql.Time

@Composable
fun SleepPrompt(logViewModel: LogViewModel) {
    val selectedTime = logViewModel.getText(LogPrompt.Sleep)
    var hoursSlept by remember {
        mutableStateOf(
            if (selectedTime != "")
                Time.valueOf(selectedTime).hours.toString()
            else
                ""
        )
    }
    var minutesSlept by remember {
        mutableStateOf(
            if (selectedTime != "")
                Time.valueOf(selectedTime).minutes.toString()
            else
                ""
        )
    }
    val focusManager = LocalFocusManager.current

    Column() {
        EditNumberField(
            label = R.string.hours,
            value = hoursSlept,
            onValueChange = { hoursSlept = it },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    val time = Time(
                        if (hoursSlept != "") hoursSlept.toInt() else 0,
                        if (minutesSlept != "") minutesSlept.toInt() else 0,
                        0
                    )
                    logViewModel.setText(LogPrompt.Sleep, time.toString())
                })
        )
        Spacer(modifier = Modifier.size(5.dp))
        EditNumberField(
            label = R.string.minutes,
            value = minutesSlept,
            onValueChange = { minutesSlept = it },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    val time = Time(
                        if (hoursSlept != "") hoursSlept.toInt() else 0,
                        if (minutesSlept != "") minutesSlept.toInt() else 0,
                        0
                    )
                    logViewModel.setText(LogPrompt.Sleep, time.toString())
                })
        )
        Button(
            onClick = {
                logViewModel.resetText(LogPrompt.Sleep)
                hoursSlept = ""
                minutesSlept = ""
                      },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(200, 200, 200)),
            modifier = Modifier
                .padding(start = 16.dp)
        ) {
            Text(
                "Clear"
            )
        }
    }
}

@Preview
@Composable
fun SleepPromptPreview() {
    SleepPrompt(
        logViewModel = LogViewModel(
            logPrompts = listOf(LogPrompt.Sleep)
        )
    )
}