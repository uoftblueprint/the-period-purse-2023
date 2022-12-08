package com.example.theperiodpurse.data

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.AddReaction
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.symptomlog.CrampsPrompt
import com.example.theperiodpurse.ui.symptomlog.FlowPrompt
import com.example.theperiodpurse.ui.symptomlog.LogViewModel
import com.example.theperiodpurse.ui.symptomlog.MoodPrompt


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
                painter = painterResource(R.drawable.opacity_black_24dp),
                contentDescription = "Flow Icon",
                tint = color
        ) },
        prompt = { logViewModel -> FlowPrompt(logViewModel = logViewModel) }
    )
    object Mood : LogPrompt(
        title = "Mood",
        icon = { color ->
            Icon(
                imageVector = Icons.Outlined.AddReaction,
                contentDescription = "Mood Icon",
                tint = color
        ) },
        prompt = { logViewModel -> MoodPrompt(logViewModel = logViewModel) }
    )
    object Sleep : LogPrompt(
        title = "Sleep",
        icon = { color -> Icon(
                painter = painterResource(R.drawable.nightlight_black_24dp),
                contentDescription = "Sleep Icon",
                tint = color
        ) },
        prompt = {}
    )
    object Cramps : LogPrompt(
        title = "Cramps",
        icon = { color -> Icon(
                painter = painterResource(R.drawable.sentiment_very_dissatisfied_black_24dp),
                contentDescription = "Cramp Icon",
                tint = color
        ) },
        prompt = { logViewModel -> CrampsPrompt(logViewModel = logViewModel)}
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
                painter = painterResource(R.drawable.edit_black_24dp),
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
                painter = painterResource(R.drawable.water_drop_black_24dp),
                contentDescription = "FlowLight"
            )
        },
        promptTitle = "Flow"
    )
    object FlowMedium: LogSquare(
        description = "Medium",
        icon = {
            Icon(
                painter = painterResource(R.drawable.opacity_black_24dp),
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
                imageVector = Icons.Filled.WaterDrop,
                contentDescription = "FlowHeavy"
            )
        },
        promptTitle = "Flow"
    )
    object FlowSpotting: LogSquare(
        description = "Spotting",
        icon = {
            Icon(
                painter = painterResource(R.drawable.spotting),
                contentDescription = "FlowSpotting"
            )
        },
        promptTitle = "Flow"
    )
    object FlowNone: LogSquare(
        description = "None",
        icon = {
            Icon(
                painter = painterResource(R.drawable.not_interested_black_24dp),
                contentDescription = "FlowNone"
            )
        },
        promptTitle = "Flow"
    )

    object MoodHappy: LogSquare(
        description = "Happy",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_satisfied_black_24dp),
                contentDescription = "MoodHappy"
            )
        },
        promptTitle = "Mood"
    )

    object MoodNeutral: LogSquare(
        description = "Meh",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_neutral_black_24dp),
                contentDescription = "MoodNeutral"
            )
        },
        promptTitle = "Mood"
    )

    object MoodSad: LogSquare(
        description = "Sad",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_dissatisfied_black_24dp),
                contentDescription = "MoodSad"
            )
        },
        promptTitle = "Mood"
    )

    object MoodSilly: LogSquare(
        description = "Silly/Goofy",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_very_satisfied_black_24dp),
                contentDescription = "MoodSilly"
            )
        },
        promptTitle = "Mood"
    )

    object MoodSick: LogSquare(
        description = "Sick",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sick_black_24dp),
                contentDescription = "MoodSick",
            )
        },
        promptTitle = "Mood"
    )

    object MoodAngry: LogSquare(
        description = "Angry",
        icon = {
            Icon(
                imageVector = Icons.Rounded.MoodBad,
                contentDescription = "MoodAngry",
            )
        },
        promptTitle = "Mood"
    )

    object MoodLoved: LogSquare(
        description = "Loved",
        icon = {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = "MoodLoved",
            )
        },
        promptTitle = "Mood"
    )

    object CrampsNeutral: LogSquare(
        description = "Neutral",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentNeutral,
                contentDescription = "CrampsNeutral",
            )
        },
        promptTitle = "Cramps"
    )

    object CrampsBad: LogSquare(
        description = "Bad",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentDissatisfied,
                contentDescription = "CrampsBad",
            )
        },
        promptTitle = "Cramps"
    )

    object CrampsTerrible: LogSquare(
        description = "Terrible",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentVeryDissatisfied,
                contentDescription = "CrampsTerrible",
            )
        },
        promptTitle = "Cramps"
    )

    object CrampsNone: LogSquare(
        description = "None",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentVerySatisfied,
                contentDescription = "CrampsNone",
            )
        },
        promptTitle = "Cramps"
    )
}