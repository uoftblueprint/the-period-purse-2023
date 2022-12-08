package com.example.theperiodpurse.data

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.LogPrompt.Cramps.iconTint
import com.example.theperiodpurse.ui.calendar.LogSelectableSquare
import com.example.theperiodpurse.ui.calendar.LogViewModel


typealias ComposablePromptFun = @Composable (logViewModel: LogViewModel) -> Unit
typealias ComposableIconFun = @Composable () -> Unit

open class LogPrompt(var title: String, var icon: ComposableIconFun, var prompt: ComposablePromptFun) {
    val iconTint = Color(50,50,50)
    object Flow : LogPrompt(
        title = "Flow",
        icon = { Icon(
            painter = painterResource(id = R.drawable.opacity_black_24dp),
            contentDescription = "Flow Icon",
            tint = iconTint
        ) },
        prompt = { logViewModel ->
            val logSquares = listOf(
                LogSquare.FlowLight,
                LogSquare.FlowMedium,
                LogSquare.FlowHeavy,
                LogSquare.FlowSpotting,
                LogSquare.FlowNone
            )
            var selected by remember { mutableStateOf<String?>(null) }
            Column() {
                Text("Some Test Text")
                LogSelectableSquare(
                    logSquare = LogSquare.FlowLight,
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
    object Mood : LogPrompt(
        title = "Mood",
        icon = { Icon(
            painter = painterResource(id = R.drawable.sentiment_very_satisfied_black_24dp),
            contentDescription = "Mood Icon",
            tint = iconTint
        ) },
        prompt = {}
    )
    object Sleep : LogPrompt(
        title = "Sleep",
        icon = { Icon(
            painter = painterResource(id = R.drawable.nightlight_black_24dp),
            contentDescription = "Sleep Icon",
            tint = iconTint
        ) },
        prompt = {}
    )
    object Cramps : LogPrompt(
        title = "Cramps",
        icon = { Icon(
            painter = painterResource(id = R.drawable.sentiment_very_dissatisfied_black_24dp),
            contentDescription = "Cramp Icon",
            tint = iconTint
        ) },
        prompt = {}
    )
    object Exercise : LogPrompt(
        title = "Exercise",
        icon = { Icon(
            painter = painterResource(id =R.drawable.self_improvement_black_24dp),
            contentDescription = "Exercise Icon",
            tint = iconTint
        ) },
        prompt = {}
    )
    object Notes : LogPrompt(
        title = "Notes",
        icon = { Icon(
            painter = painterResource(id = R.drawable.edit_black_24dp),
            contentDescription = "Notes Icon",
            tint = iconTint
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