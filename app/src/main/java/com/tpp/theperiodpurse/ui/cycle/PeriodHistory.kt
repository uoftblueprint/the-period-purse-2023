package com.tpp.theperiodpurse.ui.cycle

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tpp.theperiodpurse.AppViewModel
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.addOneDay
import com.tpp.theperiodpurse.data.parseDatesIntoPeriods
import java.text.SimpleDateFormat

import java.util.*

@Composable
fun PeriodHistoryLayout(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
    navController: NavController,
) {
    val dates = ArrayList(appViewModel.getDates())
    val bg = painterResource(R.drawable.colourwatercolour)
    Scaffold(
        topBar = {
            TopAppBar (
                {Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = "Period History",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )},
                backgroundColor = Color.White,
                elevation = 0.dp,
                navigationIcon = {
                    IconButton(onClick = {navController.navigateUp()}) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back_button)
                        )
                    }
                },
            )
        },
    )
    {
        Box(
            modifier = Modifier.padding(it)
        ) {
            Image(
                painter = bg,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .semantics { contentDescription = "Cycle Page" },
                contentScale = ContentScale.FillBounds,
            )
            Column(
                modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .fillMaxHeight()
                    .padding(horizontal = 20.dp, vertical = 25.dp)
            ) {
                Card(
                    modifier.fillMaxWidth(), elevation = 2.dp, shape = RoundedCornerShape(10)
                ) {
                    Column(modifier.padding(horizontal = 15.dp, vertical = 15.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                // replace with year
                                text = stringResource(R.string.period_history),
                                fontSize = 15.sp,
                                fontWeight = FontWeight(700),
                                color = Color(0xFF868083),
                            )
                        }
                        Divider(
                            color = Color(0xFF868083),
                            modifier = modifier
                                .padding(top = 5.dp, bottom = 10.dp)
                                .fillMaxWidth()
                        )
                        // show last three most recent periods
                        PeriodEntries(dates)
                    }
                }
            }
        }
    }
}

@Composable
fun PeriodEntries(dates: ArrayList<com.tpp.theperiodpurse.data.Date>) {
    if (dates.size == 0) {
        Text(text = stringResource(R.string.please_start_logging_to_learn_more))
    } else {
        val periods = parseDatesIntoPeriods(dates)
        val length = Integer.min(periods.size, 3)
        Column {
            val formatter = SimpleDateFormat("MMM d", Locale.getDefault())
            // check if it's within a day, if so display current period...
            periods[length - 1][0].date?.let {
                val date = formatter.format(it)
                Text(text = "Most Recent Period: Started $date")
            }
            for (i in 1 until length) {
                val period = periods[length - i - 1]
                val startDate = period[0].date
                val endDate =
                    period[period.size - 1].date?.let { addOneDay(it) }
                if (startDate != null && endDate != null) {
                    val startString = formatter.format(startDate)
                    val endString = formatter.format(endDate)
                    val periodLength =
                        (endDate.time - startDate.time) / 86400000
                    Text(text = "$startString - $endString")
                    Text(
                        text = "$periodLength-day period",
                        fontSize = 13.sp
                    )
                }
            }
        }
    }
}