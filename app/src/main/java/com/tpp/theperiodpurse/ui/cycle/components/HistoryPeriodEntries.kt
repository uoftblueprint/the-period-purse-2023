package com.tpp.theperiodpurse.ui.cycle.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.addOneDay
import com.tpp.theperiodpurse.data.entity.Date
import com.tpp.theperiodpurse.ui.onboarding.scaledSp
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.*
import kotlin.collections.ArrayList

@Composable
fun PeriodEntries(periods: ArrayList<ArrayList<Date>>, upperbound: Int?) {
    if (periods.size == 0 || periods[0].size == 0) {
        Text(text = stringResource(R.string.please_start_logging_to_learn_more))
    } else {
        val length = if (upperbound != null) kotlin.math.min(periods.size, upperbound) else periods.size
        periods.reverse()
        Column {
            val formatter = SimpleDateFormat("MMM d", Locale.getDefault())
            // If entry is within a day, display the current period
            for (i in 0 until length) {
                val date = periods[i][0].date
                val converted = date.toInstant()?.atZone(ZoneId.systemDefault())
                    ?.toLocalDate()?.year
                val current = LocalDate.now().year
                if (i == 0 && converted == current) {
                    val formattedDate = formatter.format(date)
                    // Only if same year
                    Text(text = "Most Recent Period: Started $formattedDate")
                } else {
                    val period = periods[i]
                    val startDate = period[0].date
                    val endDate = addOneDay(period[period.size - 1].date)
                    if (startDate != null && endDate != null) {
                        val startString = formatter.format(startDate)
                        val endString = formatter.format(endDate)
                        val periodLength =
                            (endDate.time - startDate.time) / 86400000
                        Text(text = "$startString - $endString")
                        Text(
                            text = "$periodLength-day period",
                            fontSize = 13.scaledSp()
                        )
                    }
                }
            }
        }
    }
}