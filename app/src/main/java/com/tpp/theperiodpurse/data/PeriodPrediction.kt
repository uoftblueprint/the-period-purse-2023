package com.tpp.theperiodpurse.data

import java.util.*
import kotlin.collections.ArrayList

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
 * Sorts an array list of Dates that removes the dates with no flow (i.e., filtering by dates
 * with periods), and sorting the list by incrementing date.
 */
fun sortPeriodHistory(periodHistory: ArrayList<Date>) {
    // Removes all dates with no flow or spotting.
    periodHistory.removeAll{ it.flow == FlowSeverity.None || it.flow == FlowSeverity.Spotting }

    // Sorts dates in ascending order.
    periodHistory.sortWith {
            date1, date2 -> date1.date.compareTo(date2.date)
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
    if (periodHistory.size == 0) {
        return (-1).toFloat()
    }
    val periodLengths = ArrayList<Int>()

    sortPeriodHistory(periodHistory)

    var currDate = periodHistory[0].date
    var consecutiveDays = 1

    for (i in 1 until periodHistory.size) {
        val currDatePlusOne = addOneDay(currDate)

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
*      - Period 1: Started Jan 1, Period 2: Started Jan 4, Period 3: Started Jan 3
*                  ==> Cycles of 3 and 9 days; average cycle length is 6 days.
*/
fun calculateAverageCycleLength(periodHistory: ArrayList<Date>): Float {
    if (periodHistory.size == 0) {
        return (-1).toFloat()
    }

    val cycleLengths = ArrayList<Int>()

    sortPeriodHistory(periodHistory)

    var currDate = periodHistory[0].date
    var currDatePlusOne = addOneDay(currDate)

    for (i in 1 until periodHistory.size) {
        if (currDatePlusOne == periodHistory[i].date) {
            currDatePlusOne = addOneDay(currDatePlusOne)
        } else {
            // Takes the time and subtracts it, then divides to convert from milliseconds to days.
            val time = (periodHistory[i].date.time - currDate.time) / 86400000
            cycleLengths.add(time.toInt())

            currDate = periodHistory[i].date
            currDatePlusOne = addOneDay(currDate)
        }
    }
    return if (cycleLengths.size == 0) {
        (-2).toFloat()
    } else {
        (cycleLengths.sum().toFloat() / cycleLengths.size)
    }
}