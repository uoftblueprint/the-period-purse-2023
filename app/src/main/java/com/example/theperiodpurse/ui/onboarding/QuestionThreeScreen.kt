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

import androidx.compose.foundation.selection.selectable
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.DataSource.symptoms
import com.example.theperiodpurse.ui.component.FormattedPriceLabel
import android.content.Context
import android.widget.RadioGroup
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*

import androidx.compose.ui.platform.LocalContext
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
){
    var selectedValue by rememberSaveable { mutableStateOf("") }
    Column(
        modifier = Modifier.padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
        Row(modifier = Modifier.padding(32.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Text(
                text = stringResource(R.string.flow),
                fontSize = 15.sp,

            )
            Text(
                text = stringResource(R.string.caption_three),
                fontSize = 15.sp,

            )

        }
        Divider(thickness = 1.dp, modifier = modifier.padding(bottom = 16.dp))

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
            Spacer(Modifier.height(24.dp))
            Divider(thickness = 1.dp, modifier = modifier.padding(bottom = 16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedButton(modifier = Modifier.weight(1f), onClick = onNextButtonClicked) {
                    Text(stringResource(R.string.skip))
                }
                Button(
                    modifier = Modifier.weight(1f),
                    // the button is enabled when the user makes a selection
                    enabled = selectedValue.isNotEmpty(),
                    onClick = onNextButtonClicked
                ) {
                    Text(stringResource(R.string.next))
                }
            }
        }
    }
}




@Preview
@Composable
fun SelectOptionPreview(){
    val context = LocalContext.current
    QuestionThreeScreen(


        options = symptoms.map { id -> context.resources.getString(id) }
    )
}


