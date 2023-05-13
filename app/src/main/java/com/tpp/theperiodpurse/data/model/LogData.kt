package com.tpp.theperiodpurse.data.model

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddReaction
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.ui.symptomlog.*
import com.tpp.theperiodpurse.ui.viewmodel.LogViewModel


typealias ComposablePromptFun = @Composable (logViewModel: LogViewModel) -> Unit
typealias ComposableIconFun = @Composable (color: Color) -> Unit

enum class LogPrompt(
    var title: Int,
    var icon: ComposableIconFun,
    var prompt: ComposablePromptFun,
    ) {
    Flow(
        title = R.string.period_flow,
        icon = { color ->
            Icon(
                painter = painterResource(R.drawable.opacity_black_24dp),
                contentDescription = "Flow Icon",
                tint = color
        ) },
        prompt = { logViewModel -> FlowPrompt(logViewModel = logViewModel) }
    ),
    Mood(
        title = R.string.mood,
        icon = { color ->
            Icon(
                imageVector = Icons.Outlined.AddReaction,
                contentDescription = "Mood Icon",
                tint = color
        ) },
        prompt = { logViewModel -> MoodPrompt(logViewModel = logViewModel) }
    ),
    Sleep(
        title = R.string.sleep,
        icon = { color -> Icon(
                painter = painterResource(R.drawable.nightlight_black_24dp),
                contentDescription = "Sleep Icon",
                tint = color
        ) },
        prompt = { logViewModel -> SleepPrompt(logViewModel = logViewModel) }
    ),
    Cramps(
        title = R.string.cramps,
        icon = { color -> Icon(
                painter = painterResource(R.drawable.sentiment_very_dissatisfied_black_24dp),
                contentDescription = "Cramp Icon",
                tint = color
        ) },
        prompt = { logViewModel -> CrampsPrompt(logViewModel = logViewModel)}
    ),
    Exercise(
        title = R.string.exercise,
        icon = { color -> Icon(
                painter = painterResource(id =R.drawable.self_improvement_black_24dp),
                contentDescription = "Exercise Icon",
                tint = color
        ) },
        prompt = { logViewModel -> ExercisePrompt(logViewModel = logViewModel) }
    ),
    Notes(
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
    var promptTitle: Int,
    var dataName: String
    ) {
    object FlowLight: LogSquare(
        description = "Light",
        icon = {
            Icon(
                painter = painterResource(R.drawable.water_drop_black_24dp),
                contentDescription = "FlowLight"
            )
        },
        promptTitle = R.string.period_flow,
        dataName = FlowSeverity.Light.name
    )
    object FlowMedium: LogSquare(
        description = "Medium",
        icon = {
            Icon(
                painter = painterResource(R.drawable.opacity_black_24dp),
                contentDescription = "FlowMedium"
            )
        },
        promptTitle = R.string.period_flow,
        dataName = FlowSeverity.Medium.name
    )
    object FlowHeavy: LogSquare(
        description = "Heavy",
        icon = {
            Icon(
                painter = painterResource(R.drawable.flow_heavy),
                contentDescription = "FlowHeavy"
            )
        },
        promptTitle = R.string.period_flow,
        dataName = FlowSeverity.Heavy.name
    )
    object FlowSpotting: LogSquare(
        description = "Spotting",
        icon = {
            Icon(
                painter = painterResource(R.drawable.spotting),
                contentDescription = "FlowSpotting"
            )
        },
        promptTitle = R.string.period_flow,
        dataName = FlowSeverity.Spotting.name
    )
    object FlowNone: LogSquare(
        description = "None",
        icon = {
            Icon(
                painter = painterResource(R.drawable.not_interested_black_24dp),
                contentDescription = "FlowNone"
            )
        },
        promptTitle = R.string.period_flow,
        dataName = FlowSeverity.None.name
    )

    object MoodHappy: LogSquare(
        description = "Happy",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_satisfied_black_24dp),
                contentDescription = "MoodHappy"
            )
        },
        promptTitle = R.string.mood,
        dataName = Mood.HAPPY.displayName
    )

    object MoodNeutral: LogSquare(
        description = "Meh",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_neutral_black_24dp),
                contentDescription = "MoodNeutral"
            )
        },
        promptTitle = R.string.mood,
        dataName = Mood.NEUTRAL.displayName
    )

    object MoodSad: LogSquare(
        description = "Sad",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_dissatisfied_black_24dp),
                contentDescription = "MoodSad"
            )
        },
        promptTitle = R.string.mood,
        dataName = Mood.SAD.displayName
    )

    object MoodSilly: LogSquare(
        description = "Silly/Goofy",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sentiment_very_satisfied_black_24dp),
                contentDescription = "MoodSilly"
            )
        },
        promptTitle = R.string.mood,
        dataName = Mood.LOL.displayName
    )

    object MoodSick: LogSquare(
        description = "Sick",
        icon = {
            Icon(
                painter = painterResource(R.drawable.sick_black_24dp),
                contentDescription = "MoodSick",
            )
        },
        promptTitle = R.string.mood,
        dataName = Mood.SICK.displayName
    )

    object MoodAngry: LogSquare(
        description = "Angry",
        icon = {
            Icon(
                imageVector = Icons.Rounded.MoodBad,
                contentDescription = "MoodAngry",
            )
        },
        promptTitle = R.string.mood,
        dataName = Mood.ANGRY.displayName
    )

    object MoodLoved: LogSquare(
        description = "Loved",
        icon = {
            Icon(
                imageVector = Icons.Rounded.Favorite,
                contentDescription = "MoodLoved",
            )
        },
        promptTitle = R.string.mood,
        dataName = Mood.LOVED.displayName
    )

    object CrampsNeutral: LogSquare(
        description = "Neutral",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentNeutral,
                contentDescription = "CrampsNeutral",
            )
        },
        promptTitle = R.string.cramps,
        dataName = CrampSeverity.Neutral.name
    )

    object CrampsBad: LogSquare(
        description = "Bad",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentDissatisfied,
                contentDescription = "CrampsBad",
            )
        },
        promptTitle = R.string.cramps,
        dataName = CrampSeverity.Bad.name
    )

    object CrampsTerrible: LogSquare(
        description = "Terrible",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentVeryDissatisfied,
                contentDescription = "CrampsTerrible",
            )
        },
        promptTitle = R.string.cramps,
        dataName = CrampSeverity.Terrible.name
    )

    object CrampsNone: LogSquare(
        description = "None",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SentimentVerySatisfied,
                contentDescription = "CrampsNone",
            )
        },
        promptTitle = R.string.cramps,
        dataName = CrampSeverity.None.name
    )

    object ExerciseCardio : LogSquare(
        description = "Cardio",
        icon = {
            Icon(
                imageVector = Icons.Rounded.DirectionsRun,
                contentDescription = "ExerciseCardio",
            )
        },
        promptTitle = R.string.exercise,
        dataName = Exercise.CARDIO.displayName
    )

    object ExerciseYoga : LogSquare(
        description = "Yoga",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SelfImprovement,
                contentDescription = "ExerciseYoga",
            )
        },
        promptTitle = R.string.exercise,
        dataName = Exercise.YOGA.displayName
    )

    object ExerciseStrength : LogSquare(
        description = "Strength",
        icon = {
            Icon(
                imageVector = Icons.Rounded.FitnessCenter,
                contentDescription = "ExerciseStrength",
            )
        },
        promptTitle = R.string.exercise,
        dataName = Exercise.STRENGTH.displayName
    )

    object ExerciseBallSports : LogSquare(
        description = "Ball Sports",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SportsSoccer,
                contentDescription = "ExerciseBallSports",
            )
        },
        promptTitle = R.string.exercise,
        dataName = Exercise.BALL_SPORTS.displayName
    )

    object ExerciseMartialArts : LogSquare(
        description = "Martial Arts",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SportsMartialArts,
                contentDescription = "ExerciseMartialArts",
            )
        },
        promptTitle = R.string.exercise,
        dataName = Exercise.MARTIAL_ARTS.displayName
    )

    object ExerciseWaterSports : LogSquare(
        description = "Water Sports",
        icon = {
            Icon(
                imageVector = Icons.Rounded.Pool,
                contentDescription = "Water Sports",
            )
        },
        promptTitle = R.string.exercise,
        dataName = Exercise.WATER_SPORTS.displayName
    )

    object ExerciseCycling : LogSquare(
        description = "Cycling",
        icon = {
            Icon(
                imageVector = Icons.Rounded.DirectionsBike,
                contentDescription = "ExerciseCycling",
            )
        },
        promptTitle = R.string.exercise,
        dataName = Exercise.CYCLING.displayName
    )

    object ExerciseRacquetSports : LogSquare(
        description = "Racquet Sports",
        icon = {
            Icon(
                imageVector = Icons.Rounded.SportsTennis,
                contentDescription = "ExerciseRacquetSports",
            )
        },
        promptTitle = R.string.exercise,
        dataName = Exercise.RACKET_SPORTS.displayName
    )

    object ExerciseWinterSports : LogSquare(
        description = "Winter Sports",
        icon = {
            Icon(
                imageVector = Icons.Rounded.DownhillSkiing,
                contentDescription = "ExerciseWinterSports",
            )
        },
        promptTitle = R.string.exercise,
        dataName = Exercise.WINTER_SPORTS.displayName
    )
}