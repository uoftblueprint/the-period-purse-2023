package com.tpp.theperiodpurse

import com.tpp.theperiodpurse.data.*
import java.text.SimpleDateFormat

val periodHistoryEmpty = arrayListOf<Date>()

val periodHistoryUnsorted = arrayListOf(
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("03/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("03/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("03/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("01/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("01/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("01/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("04/01/2023")!!,
        flow = FlowSeverity.None,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("04/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("04/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("02/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("02/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("02/01/2023")!!
    ),
)

val periodHistoryOneCycle = arrayListOf(
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("01/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("01/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("01/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("02/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("02/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("02/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("03/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("03/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("03/01/2023")!!
    )
)

val periodHistoryTwoCycles = arrayListOf(
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("01/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("01/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("01/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("02/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("02/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("02/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("01/02/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("01/02/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("01/02/2023")!!
    ),
)

// 01/01, 02/01,  04/01, 05/01, 06/01,  13/01
val periodHistoryThreeCycles = arrayListOf(
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("01/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("01/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("01/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("02/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("02/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("02/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("04/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("04/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("04/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("05/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("05/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("05/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("06/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("06/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("06/01/2023")!!
    ),
    Date(
        date = SimpleDateFormat("dd/MM/yyyy").parse("13/01/2023")!!,
        flow = FlowSeverity.Light,
        mood = Mood.HAPPY,
        exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("13/01/2023")!!,
        exerciseType = Exercise.CARDIO,
        crampSeverity = CrampSeverity.None,
        sleep = SimpleDateFormat("dd/MM/YYYY").parse("13/01/2023")!!
    )

)