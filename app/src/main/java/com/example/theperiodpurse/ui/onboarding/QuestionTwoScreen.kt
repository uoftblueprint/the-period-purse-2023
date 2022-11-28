package com.example.theperiodpurse.ui.onboarding

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.theperiodpurse.R
import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import java.util.*


@Composable
fun QuestionTwoScreen(
    onNextButtonClicked: () -> Unit,
    onSelectionChanged: (String) -> Unit = {},
    options: List<String>,
    modifier: Modifier = Modifier
) {

    var selectedValue by rememberSaveable { mutableStateOf("") }



    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.question_two),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(R.string.description_one),
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))


        Divider(thickness = 1.dp, modifier = modifier.padding(bottom = 16.dp))
        options.forEach { item ->
            Row(
                modifier = Modifier.selectable(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                ),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = selectedValue == item,
                    onClick = {
                        selectedValue = item
                        onSelectionChanged(item)
                    }
                )
                Text(item)
            }
        }
        Spacer(Modifier.height(24.dp))
        Divider(thickness = 1.dp, modifier = modifier.padding(bottom = 16.dp))




        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onNextButtonClicked,
                modifier = modifier.weight(1f),
            ) {
                Text(stringResource(R.string.skip))
            }
            Button(
                onClick = onNextButtonClicked,
                enabled = selectedValue.isNotEmpty(),
                modifier = modifier.weight(1f),
            ) {
                Text(stringResource(R.string.next))
            }

        }

    }

}


@Preview
@Composable
fun QuestionTwoPreview() {
    QuestionTwoScreen(onNextButtonClicked = { }, options = listOf(""))

}