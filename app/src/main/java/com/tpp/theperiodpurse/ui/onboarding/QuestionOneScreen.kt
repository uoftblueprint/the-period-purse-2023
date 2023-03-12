package com.tpp.theperiodpurse.ui.onboarding

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tpp.theperiodpurse.R


@Composable
fun QuestionOneScreen(onNextButtonClicked: () -> Unit,
                      onSelectionChanged: (String) -> Unit = {},
                      onCancelButtonClicked: () -> Unit = {},
                      modifier: Modifier = Modifier) {
    var periodCycle by remember { mutableStateOf("") }
    var entered by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier.padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)

    ) {
        Text(
            text = stringResource(R.string.question_one),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = stringResource(R.string.description_one),
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.height(16.dp))

        EditNumberField(
            label = R.string.tap_to_input,
            value = periodCycle,
            onValueChange = { periodCycle = it },

            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() ; onSelectionChanged(periodCycle) ; entered = true })
        )
        Spacer(Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = onNextButtonClicked,
                modifier = modifier.weight(1f)
            ) {
                Text(stringResource(R.string.skip))
            }

            Button(
                onClick = onNextButtonClicked,
                enabled = entered,
                modifier =modifier.weight(1f)
            ) {
                Text(stringResource(R.string.next))
            }
        }



    }

}


@Composable
fun EditNumberField(
    @StringRes label: Int,
    value: String,
    keyboardOptions: KeyboardOptions,
    onValueChange: (String) -> Unit,
    keyboardActions: KeyboardActions,
){


    TextField(
        value = value,
        onValueChange = onValueChange,

        label = { Text(stringResource(label)) },
        modifier = Modifier.fillMaxWidth(),
        singleLine = true,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions

    )
}

@Preview
@Composable
fun QuestionOnePreview(){
    QuestionOneScreen(onNextButtonClicked = { })

}

