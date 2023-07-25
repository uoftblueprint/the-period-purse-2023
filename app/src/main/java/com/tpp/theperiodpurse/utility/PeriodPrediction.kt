package com.tpp.theperiodpurse.data

import com.tpp.theperiodpurse.data.entity.Date
import com.tpp.theperiodpurse.data.model.FlowSeverity
import java.lang.Float.min
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.roundToInt
import java.util.Date as Date1

val c: Calendar = Calendar.getInstance()

/**
 * Increases a Java date input by one day. Returns the new date.
 */
fun addOneDay(date: java.util.Date): java.util.Date {
    c.time = date
    c.add(Calendar.DATE, 1)
    return c.time
}

/**
 * Given a list of Dates, sort the Dates into lists of periods
 * Return a list containing sublists where each sublist is a period
 */
fun parseDatesIntoPeriods(periodHistory: ArrayList<Date>): ArrayList<ArrayList<Date>> {
    sortPeriodHistory(periodHistory)
    val periods = ArrayList<ArrayList<Date>>()
    var currPeriod = ArrayList<Date>()

    if (periodHistory.size == 0) {
        return periods
    }

    for (i in 0 until periodHistory.size) {
        // check if next element in the array is within one day
        if (i == 0) {
            currPeriod.add(periodHistory[i])
        } else {
            val date1 = periodHistory[i].date
            val date2 = periodHistory[i - 1].date
            if (date1 != null && date2 != null) {
                val diff = date1.time - date2.time
                if (diff <= 86400000) {
                    currPeriod.add(periodHistory[i])
                } else {
                    periods.add(currPeriod)
                    currPeriod = ArrayList()
                    currPeriod.add(periodHistory[i])
                }
            }
        }
    }
    periods.add(currPeriod)
    return periods
}

/**
 * Sorts an array list of Dates that removes the dates with no flow (i.e., filtering by dates
 * with periods), and sorting the list by incrementing date.
 */
fun sortPeriodHistory(periodHistory: ArrayList<Date>) {
    // Removes all dates with no flow or spotting.
    periodHistory.removeAll { it.flow == FlowSeverity.None || it.flow == FlowSeverity.Spotting }

    // Sorts dates in ascending order.
    periodHistory.sortWith {
            date1, date2 ->
        date1.date?.compareTo(date2.date) ?: 0
    }
}

/**
* Calculates the average period length given the user's period history. Refer to Date.kt for each
* date's parameters.
* A period contains consecutive days where flow serverity is logged, with at most 1 day between
* each logged date.
* Returns the average period length, or -1 if the ArrayList is empty.
*
* Examples:
*     - Logs on Jan. 1 and Jan. 2 ==> 2 day period
*     - Logs on Jan. 1 to Jan. 3 ==> 3 day period
*     - Logs on Jan. 1 and Jan. 5 ==> 1 day period, 1 day period
*/
fun calculateAveragePeriodLength(periodHistory: ArrayList<Date>): Float {
    sortPeriodHistory(periodHistory)

    if (periodHistory.isEmpty()) {
        return (-1).toFloat()
    }
    val periodLengths = ArrayList<Int>()

    var currDate = periodHistory[0].date
    var consecutiveDays = 1

    for (i in 1 until periodHistory.size) {
        val currDatePlusOne = currDate?.let { addOneDay(it) }

        if (currDatePlusOne == periodHistory[i].date) {
            consecutiveDays += 1
        } else {
            periodLengths.add(consecutiveDays)
            consecutiveDays = 1
        }
        currDate = periodHistory[i].date
    }
    periodLengths.add(consecutiveDays)

    return (periodLengths.sum().toFloat() / periodLengths.size)
}

/**
* Calculates the average cycle length given the user's period history. Refer to date.kt for each
* date's parameters.
* A cycle starts counting the first day of the period, and stops counting the day before the next
* period starts.
* If the ArrayList is empty, return -1. If there is only one recorded cycle, return -2.
* Otherwise, the average cycle length is returned.
*
* Examples:
*      - Period 1: Started Jan 1, Period 2: Started Feb 1 ==> 31 day cycle.
*      - Period 1: Started Jan 1, Period 2: Started Jan 4, Period 3: Started Jan 13
*                  ==> Cycles of 3 and 9 days; average cycle length is 6 days.
*/
fun calculateAverageCycleLength(periodHistory: ArrayList<Date>): Float {
    sortPeriodHistory(periodHistory)

    if (periodHistory.size == 0) {
        return (-1).toFloat()
    }

    val cycleLengths = ArrayList<Int>()

    var currDate = periodHistory[0].date
    var currDatePlusOne = currDate?.let { addOneDay(it) }

    for (i in 1 until periodHistory.size) {
        if (currDatePlusOne == periodHistory[i].date) {
            currDatePlusOne = currDatePlusOne?.let { addOneDay(it) }
        } else {
            // Takes the time and subtracts it, then divides to convert from milliseconds to days.

            if (periodHistory[i].date != null) {
                val time = ((periodHistory[i].date?.time ?: 0) - (currDate?.time ?: 0)) / 86400000
                cycleLengths.add(time.toInt())

                currDate = periodHistory[i].date

                currDatePlusOne = currDate?.let { addOneDay(it) }
            }
        }
    }
    return if (cycleLengths.size == 0) {
        (-2).toFloat()
    } else {
        (cycleLengths.sum().toFloat() / cycleLengths.size)
    }
}

/**
 * Return the number of days since the last period
 */
fun calculateDaysSinceLastPeriod(periodHistory: ArrayList<Date>): Long {
    if (periodHistory.isEmpty()) {
        return 0
    }
    sortPeriodHistory(periodHistory)
    val lastPeriodDate = periodHistory[periodHistory.size - 1].date?.time
    val currDate = Date1.from(
        LocalDateTime.of(
            LocalDate.now(),
            LocalDateTime.MIN.toLocalTime(),
        ).atZone(ZoneId.systemDefault()).toInstant(),
    ).time
    if (lastPeriodDate != null) {
        return (currDate - lastPeriodDate) / 86400000
    }
    return 0
}

/**
 * Calculate the the sweeping angle for the progress circle
 * assume one month contains 31 days, use the equation:
 * (number of days since last cycle / 31) * 360f
 */
fun calculateArcAngle(periodHistory: ArrayList<Date>): Float {
    val averageCycleLength = calculateAverageCycleLength(periodHistory)
    if (averageCycleLength <= 0f) {
        return 360f * min(1f, calculateDaysSinceLastPeriod(periodHistory) / 31f)
    }
    val resultedAngle = calculateDaysSinceLastPeriod(periodHistory) / averageCycleLength
    return 360f * min(1f, resultedAngle)
}

/**
 * Given an arraylist containing sublists of periods, find and return a HashTable
 * where the keys are years, and the values are corresponding periods
 */
fun findYears(periods: ArrayList<ArrayList<Date>>): MutableMap<Int, ArrayList<ArrayList<Date>>>? {
    val years = mutableMapOf<Int, ArrayList<ArrayList<Date>>>()
    if (periods.size == 0 || periods[0].size == 0) {
        return null
    }
    for (period in periods) {
        val year = period[0].date?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()?.year
        if (years.containsKey(year)) {
            years[year]?.add(period)
        } else {
            if (year != null) {
                years[year] = ArrayList()
                years[year]?.add(period)
            }
        }
    }
    return years
}


fun getPeriodPrediction(periodHistory: ArrayList<Date>): ArrayList<java.util.Date> {
    var periodLength = calculateAveragePeriodLength(periodHistory)
    var cycleLength = calculateAverageCycleLength(periodHistory)

    var periods = parseDatesIntoPeriods(periodHistory)
    var latestCycle = periods[0].last().date

    val dayInMilliseconds = 24 * 60 * 60 * 1000

    val timeToAdd = cycleLength * dayInMilliseconds // convert to milliseconds

    // start date of next period = start date of lastest period + cycleLength days
    val newTime = (latestCycle.time + timeToAdd).toLong()
    var newDate = java.util.Date(newTime)

    var dates = ArrayList<java.util.Date>()
    dates.add(newDate)

    // create an array of all consecutive predicted dates
    for (i in 1 until periodLength.roundToInt()) {
        newDate = java.util.Date(newDate.time + dayInMilliseconds)
        dates.add(newDate)
    }

    return dates
}