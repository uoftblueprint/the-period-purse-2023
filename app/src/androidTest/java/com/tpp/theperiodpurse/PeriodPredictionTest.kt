package com.example.theperiodpurse

import com.example.theperiodpurse.data.*
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*
import org.junit.Test

@HiltAndroidTest
class PeriodPredictionTest {

    @Test
    fun sortPeriodHistory() {
        val testedList = periodHistoryUnsorted
        sortPeriodHistory(testedList)

        assertEquals(
            testedList, periodHistoryOneCycle
        )
    }


    @Test
    fun calculatePeriodLength_EmptyList() {
        assertEquals(
            (-1).toFloat(), calculateAveragePeriodLength(periodHistoryEmpty)
        )
    }
//
//    @Test
//    fun calculatePeriodLength_Unsorted() {
//        assertEquals(
//            3.toFloat(), calculateAveragePeriodLength(periodHistoryUnsorted)
//        )
//    }

    @Test
    fun calculatePeriodLength_OneCycle() {
        assertEquals(
            3.0.toFloat(), calculateAveragePeriodLength(periodHistoryOneCycle)
        )
    }

    @Test
    fun calculatePeriodLength_TwoCycles() {
        assertEquals(
            1.5.toFloat(), calculateAveragePeriodLength(periodHistoryTwoCycles)
        )
    }

    @Test
    fun calculatePeriodLength_ThreeCycles() {
        assertEquals(
            2.toFloat(), calculateAveragePeriodLength(periodHistoryThreeCycles)
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

    @Test
    fun calculateCycleLength_ThreeCycles() {
        assertEquals(
            6.toFloat(), calculateAverageCycleLength(periodHistoryThreeCycles)
        )
    }

}