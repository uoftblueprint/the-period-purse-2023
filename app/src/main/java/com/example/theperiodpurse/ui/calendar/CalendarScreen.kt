package com.example.theperiodpurse.ui.calendar

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theperiodpurse.R

class CalendarScreen {
}

sealed class Symptom(@StringRes val nameId: Int, @DrawableRes val resourceId: Int) {
    object Flow : Symptom(R.string.period_flow, R.drawable.opacity_black_24dp)
    object Exercise : Symptom(R.string.exercise, R.drawable.self_improvement_black_24dp)
    object Cramps : Symptom(R.string.cramps, R.drawable.sick_black_24dp)
    object Mood : Symptom(R.string.mood, R.drawable.sentiment_neutral_black_24dp)
    object Sleep : Symptom(R.string.sleep, R.drawable.nightlight_black_24dp)
}

val tabModifier = Modifier
    .background(Color.White)
    .fillMaxWidth()

/* TODO: retrieve this programmatically from user preferences */
val trackedSymptoms = listOf(
    Symptom.Flow,
    Symptom.Mood,
    Symptom.Exercise,
    Symptom.Cramps,
    Symptom.Sleep
)

@Composable
fun SymptomTab(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var activeSymptom: Symptom? by remember { mutableStateOf(null) }
    Column(modifier = modifier) {
        DisplaySymptomTab(
            activeSymptom = activeSymptom,
            expanded = expanded,
            onExpandButtonClick = { expanded = !expanded },
            modifier = tabModifier
        )
        if (expanded) {
            SwitchSymptomTab(
                activeSymptom = activeSymptom,
                symptoms = trackedSymptoms,
                onSymptomOnClicks = trackedSymptoms.map { { activeSymptom = it } },
                modifier = tabModifier
            )
        }
    }
}

@Composable
private fun DisplaySymptomTab(
    activeSymptom: Symptom?,
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
            text = stringResource(activeSymptom?.nameId ?: Symptom.Flow.nameId),
            modifier = Modifier.padding(end = 2.dp)
        )
        Icon(
            painter = painterResource(
                id = activeSymptom?.resourceId ?: Symptom.Flow.resourceId
            ),
            tint = Color.Black,
            contentDescription = null,
            modifier = Modifier.padding(end = 0.dp)
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
    activeSymptom: Symptom?,
    symptoms: List<Symptom>,
    onSymptomOnClicks: List<() -> Unit>,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = modifier
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
                } else if (activeSymptom == symptom) {
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
fun DisplaySymptomTabPreview() {
    SymptomTab()
}