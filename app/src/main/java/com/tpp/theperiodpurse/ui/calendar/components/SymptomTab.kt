package com.tpp.theperiodpurse.ui.calendar.components

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.model.Symptom
import com.tpp.theperiodpurse.ui.calendar.tabModifier

@Composable
fun SymptomTab(
    trackedSymptoms: List<Symptom>,
    selectedSymptom: Symptom,
    onSymptomClick: (Symptom) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        DisplaySymptomTab(
            selectedSymptom = selectedSymptom,
            expanded = expanded,
            onExpandButtonClick = { expanded = !expanded },
            modifier = tabModifier
        )
        if (expanded) {
            SwitchSymptomTab(
                selectedSymptom = selectedSymptom,
                symptoms = trackedSymptoms,
                onSymptomOnClicks = trackedSymptoms.map { { onSymptomClick(it) } },
                modifier = tabModifier
            )
        }
    }
}

@Composable
private fun DisplaySymptomTab(
    selectedSymptom: Symptom,
    expanded: Boolean,
    onExpandButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        Text(
            text = stringResource(selectedSymptom.nameId),
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(end = 2.dp)
        )
        Icon(
            painter = painterResource(
                id = selectedSymptom.resourceId
            ),
            tint = Color.Black,
            contentDescription = selectedSymptom.name,
            modifier = Modifier
                .padding(end = 0.dp)
                .testTag("Selected Symptom")
        )
        SwitchSymptomButton(
            expanded = expanded,
            onClick = onExpandButtonClick
        )
    }
}

@Composable
private fun SwitchSymptomButton(
    expanded: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(onClick = onClick, modifier = modifier) {
        Icon(
            imageVector = if (expanded) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
            tint = Color.Gray,
            contentDescription = stringResource(R.string.expand_button_symptoms_content_description)
        )
    }
}

@Composable
private fun SwitchSymptomTab(
    selectedSymptom: Symptom,
    symptoms: List<Symptom>,
    onSymptomOnClicks: List<() -> Unit>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier.testTag("Symptom Options")
    ) {
        symptoms.zip(onSymptomOnClicks).forEach { (symptom, onClick) ->
            val interactionSource = remember { MutableInteractionSource() }
            val isPressed by interactionSource.collectIsPressedAsState()

            IconButton(
                onClick = onClick,
                interactionSource = interactionSource
            ) {
                val defaultColor = Color.Black
                val color = if (isPressed) {
                    defaultColor.copy(ContentAlpha.disabled)
                } else if (selectedSymptom == symptom) {
                    Color(0xFFBF3428)
                } else {
                    defaultColor
                }
                Icon(
                    painter = painterResource(id = symptom.resourceId),
                    tint = color,
                    contentDescription = stringResource(symptom.nameId)
                )
            }
        }
    }
}

@Preview
@Composable
fun SymptomTabPreview() {
    var selectedSymptom by remember { mutableStateOf(Symptom.FLOW) }
    SymptomTab(
        trackedSymptoms = Symptom.values().asList(),
        selectedSymptom = selectedSymptom,
        onSymptomClick = { selectedSymptom = it }
    )
}