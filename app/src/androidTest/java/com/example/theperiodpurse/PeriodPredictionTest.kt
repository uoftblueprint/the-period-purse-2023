package com.example.theperiodpurse

import com.example.theperiodpurse.data.*
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Test
import java.text.SimpleDateFormat

@HiltAndroidTest
class PeriodPredictionTest {
    private val periodHistoryEmpty = arrayListOf<Date>()

    private val periodHistoryOneCycle = arrayListOf(
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
        )
    )

    private val periodHistoryTwoCycles = arrayListOf(
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
//    TODO: Write more tests
//    private val periodHistoryThreeCycles = arrayListOf(
//        Date(
//            date = SimpleDateFormat("dd/MM/yyyy").parse("01/01/2023")!!,
//            flow = FlowSeverity.Light,
//            mood = Mood.HAPPY,
//            exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("01/01/2023")!!,
//            exerciseType = Exercise.CARDIO,
//            crampSeverity = CrampSeverity.None,
//            sleep = SimpleDateFormat("dd/MM/YYYY").parse("01/01/2023")!!
//        ),
//        Date(
//            date = SimpleDateFormat("dd/MM/yyyy").parse("02/01/2023")!!,
//            flow = FlowSeverity.Light,
//            mood = Mood.HAPPY,
//            exerciseLength = SimpleDateFormat("dd/MM/YYYY").parse("02/01/2023")!!,
//            exerciseType = Exercise.CARDIO,
//            crampSeverity = CrampSeverity.None,
//            sleep = SimpleDateFormat("dd/MM/YYYY").parse("02/01/2023")!!
//        ),
//    )



    @Test
    fun calculatePeriodLength_EmptyList() {
        assertEquals(
            (-1).toFloat(), calculateAveragePeriodLength(periodHistoryEmpty)
        )
    }

    @Test
    fun calculatePeriodLength_OneCycle() {
        assertEquals(
            2.0.toFloat(), calculateAveragePeriodLength(periodHistoryOneCycle)
        )
    }

    @Test
    fun calculatePeriodLength_TwoCycles() {
        assertEquals(
            1.5.toFloat(), calculateAveragePeriodLength(periodHistoryTwoCycles)
        )
    }


    /**
     * Cycle Length Tests
     */

    @Test
    fun calculateCycleLength_EmptyList() {
        assertEquals(
            (-1).toFloat(), calculateAverageCycleLength(periodHistoryEmpty)
        )
    }

    @Test
    fun calculateCycleLength_OneCycle() {
        assertEquals(
            (-2).toFloat(), calculateAverageCycleLength(periodHistoryOneCycle)
        )
    }

    @Test
    fun calculateCycleLength_TwoCycles() {
        assertEquals(
            31.toFloat(), calculateAverageCycleLength(periodHistoryTwoCycles)
        )
    }
}