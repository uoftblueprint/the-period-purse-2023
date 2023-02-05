package com.example.theperiodpurse.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.selection.selectable
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.DataSource.symptoms
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp

/**
 * Composable that displays the list of items as [RadioButton] options,
 * [onSelectionChanged] lambda that notifies the parent composable when a new value is selected,
 * [onCancelButtonClicked] lambda that cancels the order when user clicks cancel and
 * [onNextButtonClicked] lambda that triggers the navigation to next screen
 */


@Composable
fun QuestionThreeScreen(
    options: List<String>,
    onSelectionChanged: (String) -> Unit = {},
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var selectedValue by rememberSaveable { mutableStateOf("") }


    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(25.dp))

        Image(
            painter = painterResource(R.drawable.cloth_pads_2x),
            contentDescription = null,
            modifier = Modifier.size(200 .dp),
        )

        Spacer(modifier = Modifier.height(25.dp))



        Text(
            text = stringResource(R.string.question_three),
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        Text(
            text = stringResource(R.string.description_three),
            fontSize = 15.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Row(
            modifier = Modifier.padding(32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = stringResource(R.string.flow),
                fontSize = 15.sp,

                )
            Text(
                text = stringResource(R.string.caption_three),
                fontSize = 15.sp,

                )

        }

        Column(modifier = modifier.padding(16.dp)) {

            options.forEach { item ->

                Row(
                    modifier = Modifier.selectable(
                        selected = selectedValue.contains(item),
                        onClick = {
                            if (selectedValue.contains(item)) {

                                selectedValue = selectedValue.replace(item, "")


                            } else if (!selectedValue.contains(item)) {
                                selectedValue = selectedValue + "|" + item

                            }
                            onSelectionChanged(selectedValue)
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    RadioButton(
                        selected = selectedValue.contains(item),
                        onClick = {
                            if (selectedValue.contains(item)) {

                                selectedValue = selectedValue.replace(item, "")


                            } else if (!selectedValue.contains(item)) {
                                selectedValue = selectedValue + "|" + item

                            }
                            onSelectionChanged(selectedValue)

                        }
                    )
                    Text(item)

                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(modifier = Modifier.weight(1f), onClick = onNextButtonClicked, colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent)) {
                    Text(stringResource(R.string.skip),color = Color(97, 153, 154), fontSize = 20.sp)
                }
                Button(
                    modifier = Modifier.weight(1f),
                    // the button is enabled when the user makes a selection
                    enabled = selectedValue.isNotEmpty(),
                    onClick = onNextButtonClicked,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(97, 153, 154))
                ) {
                    Text(stringResource(R.string.next),color = Color.White, fontSize = 20.sp)
                }
            }
        }
    }
}


@Preview
@Composable
fun SelectOptionPreview() {
    val context = LocalContext.current
    QuestionThreeScreen(


        options = symptoms.map { id -> context.resources.getString(id) }
    )
}


