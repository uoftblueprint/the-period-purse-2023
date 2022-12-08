package com.example.theperiodpurse.ui.symptomlog

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.example.theperiodpurse.data.LogSquare
import com.example.theperiodpurse.ui.calendar.LogSelectableSquare
import com.example.theperiodpurse.ui.onboarding.EditNumberField
import java.sql.Time

@Composable
fun ExercisePrompt(logViewModel: LogViewModel) {
    val selectedTime = logViewModel.getText(LogPrompt.Exercise)
    var hoursExercised by remember {
        mutableStateOf(
            if (selectedTime != "")
                Time.valueOf(selectedTime).hours.toString()
            else
                ""
        )
    }
    var minutesExercised by remember {
        mutableStateOf(
            if (selectedTime != "")
                Time.valueOf(selectedTime).minutes.toString()
            else
                ""
        )
    }
    val focusManager = LocalFocusManager.current

    Column() {
        Row(
            modifier = Modifier
                .padding(start = 10.dp, bottom = 10.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(85.dp)
            ) {
                EditNumberField(
                    label = R.string.hours,
                    value = hoursExercised,
                    onValueChange = { hoursExercised = it },

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            val time = Time(
                                if (hoursExercised != "") hoursExercised.toInt() else 0,
                                if (minutesExercised != "") minutesExercised.toInt() else 0,
                                0
                            )
                            logViewModel.setText(LogPrompt.Exercise, time.toString())
                        })
                )
            }

            Spacer(modifier = Modifier.size(17.dp))
            Box(
                modifier = Modifier
                    .width(90.dp)
            ) {
                EditNumberField(
                    label = R.string.minutes,
                    value = minutesExercised,
                    onValueChange = { minutesExercised = it },

                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                            val time = Time(
                                if (hoursExercised != "") hoursExercised.toInt() else 0,
                                if (minutesExercised != "") minutesExercised.toInt() else 0,
                                0
                            )
                            logViewModel.setText(LogPrompt.Exercise, time.toString())
                        })
                )
            }
            Spacer(modifier = Modifier.size(6.dp))
            Button(
                onClick = {
                    logViewModel.resetText(LogPrompt.Exercise)
                    hoursExercised = ""
                    minutesExercised = ""
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
        var selected by remember {
            mutableStateOf(logViewModel.getSquareSelected(logPrompt = LogPrompt.Exercise))
        }

        val flowSquares = listOf(
            LogSquare.ExerciseCardio,
            LogSquare.ExerciseYoga,
            LogSquare.ExerciseStrength,
            LogSquare.ExerciseBallSports,
            LogSquare.ExerciseMartialArts,
            LogSquare.ExerciseWaterSports,
            LogSquare.ExerciseCycling,
            LogSquare.ExerciseRacquetSports,
            LogSquare.ExerciseWinterSports
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            contentPadding = PaddingValues(
                start = 0.dp,
                top = 10.dp,
                end = 0.dp,
                bottom = 16.dp),
            modifier = Modifier
                .height(400.dp),
            content = {
                items(flowSquares) { flowSquare ->
                    LogSelectableSquare(
                        logSquare = flowSquare,
                        selected = selected
                    ) { logSquare ->
                        if (selected == logSquare.description) {
                            selected = null
                            logViewModel.resetSquareSelected(logSquare)
                        } else {
                            selected = logSquare.description
                            logViewModel.setSquareSelected(logSquare)
                        }
                    }
                }
            }
        )
    }



}

@Preview
@Composable
fun ExercisePromptPreview() {
    ExercisePrompt(
        logViewModel = LogViewModel(
            logPrompts = listOf(LogPrompt.Exercise)
        )
    )
}