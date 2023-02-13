package com.example.theperiodpurse.ui.calendar.components

import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.theperiodpurse.R
import com.example.theperiodpurse.data.*
import com.example.theperiodpurse.ui.calendar.CalendarDayUIState
import com.kizitonwose.calendar.core.CalendarDay
import java.time.LocalDate
import java.util.EnumMap

val exerciseIconVectors = EnumMap(
    mapOf(
        Exercise.CARDIO to Icons.Rounded.DirectionsRun,
        Exercise.YOGA to Icons.Rounded.SelfImprovement,
        Exercise.STRENGTH to Icons.Rounded.FitnessCenter,
        Exercise.BALL_SPORTS to Icons.Rounded.SportsSoccer,
        Exercise.MARTIAL_ARTS to Icons.Rounded.SportsMartialArts,
        Exercise.WATER_SPORTS to Icons.Rounded.Pool,
        Exercise.CYCLE_SPORTS to Icons.Rounded.DirectionsBike,
        Exercise.RACKET_SPORTS to Icons.Rounded.SportsTennis,
        Exercise.WINTER_SPORTS to Icons.Rounded.DownhillSkiing
    )
)

@Composable
fun DayImage(
    activeSymptom: Symptom,
    calendarDayUIState: CalendarDayUIState
) {
    if (activeSymptom == Symptom.EXERCISE) {
//       Icon(
//           imageVector = exerciseIconVectors[calendarDayUIState.exerciseType]!!,
//           ""
//       )
    }
}

fun getDayColorAndIcon(
    day: CalendarDay,
    activeSymptom: Symptom,
    calendarDayUIState: CalendarDayUIState?
): Pair<Color, Int> {
    val defaultColor = Color.White
    val defaultImage = R.drawable.blank
    val default = Pair(defaultColor, defaultImage)
    if (day.date.isAfter(LocalDate.now())) {
        return Pair(Color(237, 237, 237), R.drawable.blank)
    }
    if (calendarDayUIState == null) {
        return default
    }
    return when (activeSymptom) {
        Symptom.FLOW ->
            when (calendarDayUIState.flow) {
                FlowSeverity.Light -> Pair(Color(215, 125, 125), R.drawable.water_drop_black_24dp)
                FlowSeverity.Medium -> Pair(Color(210, 80, 75), R.drawable.opacity_black_24dp)
                FlowSeverity.Heavy -> Pair(Color(195, 50, 50), R.drawable.flow_heavy)
                FlowSeverity.Spotting -> Pair(Color(245, 192, 192), R.drawable.spotting)
                FlowSeverity.None, null -> default
            }
        Symptom.CRAMPS ->
            when (calendarDayUIState.crampSeverity) {
                CrampSeverity.Bad -> Pair(
                    Color(0xFFDD8502),
                    R.drawable.sentiment_dissatisfied_black_24dp
                )
                CrampSeverity.Good -> Pair(
                    Color(0xFFFFD363),
                    R.drawable.sentiment_satisfied_black_24dp
                )
                CrampSeverity.Neutral -> Pair(
                    Color(0xFFE3797A),
                    R.drawable.sentiment_neutral_black_24dp
                )
                CrampSeverity.Terrible -> Pair(
                    Color(0xFFB85A04),
                    R.drawable.sentiment_very_dissatisfied_black_24dp
                )
                CrampSeverity.None, null -> default
            }
        Symptom.EXERCISE ->
            Pair(
                when (calendarDayUIState.exerciseSeverity) {
                    ExerciseSeverity.Little -> Color(0xFFB9E0D8)
                    ExerciseSeverity.Light -> Color(0xFF7BCFC0)
                    ExerciseSeverity.Medium -> Color(0xFF5A9F93)
                    ExerciseSeverity.Heavy -> Color(0xFF2F5B54)
                    null -> defaultColor
                },
                R.drawable.blank
            )
        Symptom.MOOD ->
            Pair(
                defaultColor,
                when (calendarDayUIState.mood) {
                    "Happy" -> R.drawable.sentiment_satisfied_black_24dp
                    "Meh" -> R.drawable.sentiment_neutral_black_24dp
                    "Sad" -> R.drawable.sentiment_dissatisfied_black_24dp
                    "Silly/Goofy" -> R.drawable.sentiment_very_satisfied_black_24dp
                    "Sick" -> R.drawable.sick_black_24dp
//                    "Angry" -> Icons.Rounded.MoodBad
//                    "Loved" -> Icons.Rounded.Favorite
                    else -> R.drawable.blank
                }
            )
        Symptom.SLEEP ->
            Pair(
                when (calendarDayUIState.sleepScore) {
                    Sleep.Little -> Color(0xFF92B8F0)
                    Sleep.Light -> Color(0xFF467CCD)
                    Sleep.Medium -> Color(0xFF1A50A0)
                    Sleep.Heavy -> Color(0xFF133364)
                    null -> defaultColor
                },
                R.drawable.blank
            )
    }
}
