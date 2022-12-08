package com.example.theperiodpurse.data

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.calendar.LogSelectableSquare
import com.example.theperiodpurse.ui.symptomlog.FlowPrompt
import com.example.theperiodpurse.ui.symptomlog.LogViewModel


typealias ComposablePromptFun = @Composable (logViewModel: LogViewModel) -> Unit
typealias ComposableIconFun = @Composable (color: Color) -> Unit

open class LogPrompt(
        var title: String,
        var icon: ComposableIconFun,
        var prompt: ComposablePromptFun,
    ) {
    object Flow : LogPrompt(
        title = "Flow",
        icon = {color ->
            Icon(
                painter = painterResource(id = R.drawable.opacity_black_24dp),
                contentDescription = "Flow Icon",
                tint = color
        ) },
        prompt = { logViewModel -> FlowPrompt(logViewModel = logViewModel) }
    )
    object Mood : LogPrompt(
        title = "Mood",
        icon = { color ->
            Icon(
                painter = painterResource(id = R.drawable.sentiment_very_satisfied_black_24dp),
                contentDescription = "Mood Icon",
                tint = color
        ) },
        prompt = {}
    )
    object Sleep : LogPrompt(
        title = "Sleep",
        icon = { color -> Icon(
                painter = painterResource(id = R.drawable.nightlight_black_24dp),
                contentDescription = "Sleep Icon",
                tint = color
        ) },
        prompt = {}
    )
    object Cramps : LogPrompt(
        title = "Cramps",
        icon = { color -> Icon(
                painter = painterResource(id = R.drawable.sentiment_very_dissatisfied_black_24dp),
                contentDescription = "Cramp Icon",
                tint = color
        ) },
        prompt = {}
    )
    object Exercise : LogPrompt(
        title = "Exercise",
        icon = { color -> Icon(
                painter = painterResource(id =R.drawable.self_improvement_black_24dp),
                contentDescription = "Exercise Icon",
                tint = color
        ) },
        prompt = {}
    )
    object Notes : LogPrompt(
        title = "Notes",
        icon = { color -> Icon(
                painter = painterResource(id = R.drawable.edit_black_24dp),
                contentDescription = "Notes Icon",
                tint = color
        ) },
        prompt = {}
    )
}

open class LogSquare (
    var description: String,
    var icon: ComposableIconFun,
    var promptTitle: String
    ) {
    object FlowLight: LogSquare(
        description = "Light",
        icon = {
            Icon(
                // Missing asset
                painter = painterResource(id = R.drawable.water_drop_black_24dp),
                contentDescription = "FlowLight"
            )
        },
        promptTitle = "Flow"
    )
    object FlowMedium: LogSquare(
        description = "Medium",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.opacity_black_24dp),
                contentDescription = "FlowMedium"
            )
        },
        promptTitle = "Flow"
    )
    object FlowHeavy: LogSquare(
        description = "Heavy",
        icon = {
            Icon(
                // Missing asset
                painter = painterResource(id = R.drawable.menu_black_24dp),
                contentDescription = "FlowHeavy"
            )
        },
        promptTitle = "Flow"
    )
    object FlowSpotting: LogSquare(
        description = "Spotting",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.spotting),
                contentDescription = "FlowSpotting"
            )
        },
        promptTitle = "Flow"
    )
    object FlowNone: LogSquare(
        description = "None",
        icon = {
            Icon(
                painter = painterResource(id = R.drawable.not_interested_black_24dp),
                contentDescription = "FlowNone"
            )
        },
        promptTitle = "Flow"
    )
}