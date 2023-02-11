package com.example.theperiodpurse.data

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddReaction
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.theperiodpurse.R
import com.example.theperiodpurse.ui.symptomlog.*


typealias ComposablePromptFun = @Composable (logViewModel: LogViewModel) -> Unit
typealias ComposableIconFun = @Composable (color: Color) -> Unit

enum class LogPrompt(
    var title: Int,
    var icon: ComposableIconFun,
    var prompt: ComposablePromptFun,
    ) {
    FLOW(
        title = R.string.period_flow,
        icon = { color ->
            Icon(
                painter = painterResource(R.drawable.opacity_black_24dp),
                contentDescription = "Flow Icon",
                tint = color
        ) },
        prompt = { logViewModel -> FlowPrompt(logViewModel = logViewModel) }
    ),
    MOOD(
        title = R.string.mood,
        icon = { color ->
            Icon(
                imageVector = Icons.Outlined.AddReaction,
                contentDescription = "Mood Icon",
                tint = color
        ) },
        prompt = { logViewModel -> MoodPrompt(logViewModel = logViewModel) }
    ),
    SLEEP(
        title = R.string.sleep,
        icon = { color -> Icon(
                painter = painterResource(R.drawable.nightlight_black_24dp),
                contentDescription = "Sleep Icon",
                tint = color
        ) },
        prompt = { logViewModel -> SleepPrompt(logViewModel = logViewModel) }
    ),
    CRAMPS(
        title = R.string.cramps,
        icon = { color -> Icon(
                painter = painterResource(R.drawable.sentiment_very_dissatisfied_black_24dp),
                contentDescription = "Cramp Icon",
                tint = color
        ) },
        prompt = { logViewModel -> CrampsPrompt(logViewModel = logViewModel)}
    ),
    EXERCISE(
        title = R.string.exercise,
        icon = { color -> Icon(
                painter = painterResource(id =R.drawable.self_improvement_black_24dp),
                contentDescription = "Exercise Icon",
                tint = color
        ) },
        prompt = { logViewModel -> ExercisePrompt(logViewModel = logViewModel) }
    ),
    NOTES(
        title = R.string.notes,
        icon = { color -> Icon(
                painter = painterResource(R.drawable.edit_black_24dp),
                contentDescription = "Notes Icon",
                tint = color
        ) },
        prompt = { logViewModel -> NotesPrompt(logViewModel = logViewModel) }
    );
    companion object {
        private val map = values().associateBy { it.title }
        infix fun from(title: Int) = map[title]
    }
}

open class LogSquare (
    var description: String,
    var icon: ComposableIconFun,
    var promptTitle: Int
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
        promptTitle = R.string.period_flow
    )
    object FlowMedium: LogSquare(
        description = "Medium",
        icon = {
            Icon(
                painter = painterResource(R.drawable.opacity_black_24dp),
                contentDescription = "FlowMedium"
            )
        },
        promptTitle = R.string.period_flow
    )
    object FlowHeavy: LogSquare(
        description = "Heavy",
        icon = {
            Icon(
                painter = painterResource(R.drawable.flow_heavy),
                contentDescription = "FlowHeavy"
            )
        },
        promptTitle = R.string.period_flow
    )
    object FlowSpotting: LogSquare(
        description = "Spotting",
        icon = {
            Icon(
                painter = painterResource(R.drawable.spotting),
                contentDescription = "FlowSpotting"
            )
        },
        promptTitle = R.string.period_flow
    )
    object FlowNone: LogSquare(
        description = "None",
        icon = {
            Icon(
                painter = painterResource(R.drawable.not_interested_black_24dp),
                contentDescription = "FlowNone"
            )
        },
        promptTitle = R.string.period_flow
    )

    object MoodHappy: LogSquare(
        description = "Happy",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_satisfied_black_24dp),
                contentDescription = "MoodHappy"
            )
        },
        promptTitle = R.string.mood
    )

    object MoodNeutral: LogSquare(
        description = "Meh",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_neutral_black_24dp),
                contentDescription = "MoodNeutral"
            )
        },
        promptTitle = R.string.mood
    )

    object MoodSad: LogSquare(
        description = "Sad",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_dissatisfied_black_24dp),
                contentDescription = "MoodSad"
            )
        },
        promptTitle = R.string.mood
    )

    object MoodSilly: LogSquare(
        description = "Silly/Goofy",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_very_satisfied_black_24dp),
                contentDescription = "MoodSilly"
            )
        },
        promptTitle = R.string.mood
    )

    object MoodSick: LogSquare(
        description = "Sick",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sick_black_24dp),
                contentDescription = "MoodSick",
            )
        },
        promptTitle = R.string.mood
    )

    object MoodAngry: LogSquare(
        description = "Angry",
        icon = {
            Icon(
                imageVector = Icons.Rounded.MoodBad,
                contentDescription = "MoodAngry",
            )
        },
        promptTitle = R.string.mood
    )

    object MoodLoved: LogSquare(
        description = "Loved",
        icon = {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = "MoodLoved",
            )
        },
        promptTitle = R.string.mood
    )

    object CrampsNeutral: LogSquare(
        description = "Neutral",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentNeutral,
                contentDescription = "CrampsNeutral",
            )
        },
        promptTitle = R.string.cramps
    )

    object CrampsBad: LogSquare(
        description = "Bad",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentDissatisfied,
                contentDescription = "CrampsBad",
            )
        },
        promptTitle = R.string.cramps
    )

    object CrampsTerrible: LogSquare(
        description = "Terrible",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentVeryDissatisfied,
                contentDescription = "CrampsTerrible",
            )
        },
        promptTitle = R.string.cramps
    )

    object CrampsNone: LogSquare(
        description = "None",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentVerySatisfied,
                contentDescription = "CrampsNone",
            )
        },
        promptTitle = R.string.cramps
    )

    object ExerciseCardio : LogSquare(
        description = "Cardio",
        icon = {
            Icon(
                imageVector = Icons.Rounded.DirectionsRun,
                contentDescription = "ExerciseCardio",
            )
        },
        promptTitle = R.string.exercise
    )

    object ExerciseYoga : LogSquare(
        description = "Yoga",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SelfImprovement,
                contentDescription = "ExerciseYoga",
            )
        },
        promptTitle = R.string.exercise
    )

    object ExerciseStrength : LogSquare(
        description = "Strength",
        icon = {
            Icon(
                imageVector = Icons.Rounded.FitnessCenter,
                contentDescription = "ExerciseStrength",
            )
        },
        promptTitle = R.string.exercise
    )

    object ExerciseBallSports : LogSquare(
        description = "Ball Sports",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SportsSoccer,
                contentDescription = "ExerciseBallSports",
            )
        },
        promptTitle = R.string.exercise
    )

    object ExerciseMartialArts : LogSquare(
        description = "Martial Arts",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SportsMartialArts,
                contentDescription = "ExerciseMartialArts",
            )
        },
        promptTitle = R.string.exercise
    )

    object ExerciseWaterSports : LogSquare(
        description = "Water Sports",
        icon = {
            Icon(
                imageVector = Icons.Rounded.Pool,
                contentDescription = "Water Sports",
            )
        },
        promptTitle = R.string.exercise
    )

    object ExerciseCycling : LogSquare(
        description = "Cycling",
        icon = {
            Icon(
                imageVector = Icons.Rounded.DirectionsBike,
                contentDescription = "ExerciseCycling",
            )
        },
        promptTitle = R.string.exercise
    )

    object ExerciseRacquetSports : LogSquare(
        description = "Racquet Sports",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SportsTennis,
                contentDescription = "ExerciseRacquetSports",
            )
        },
        promptTitle = R.string.exercise
    )

    object ExerciseWinterSports : LogSquare(
        description = "Winter Sports",
        icon = {
            Icon(
                imageVector = Icons.Rounded.DownhillSkiing,
                contentDescription = "ExerciseWinterSports",
            )
        },
        promptTitle = R.string.exercise
    )
}