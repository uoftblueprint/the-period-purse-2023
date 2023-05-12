package com.tpp.theperiodpurse.ui.cycle

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.tpp.theperiodpurse.ui.viewmodel.AppViewModel
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.addOneDay
import com.tpp.theperiodpurse.data.entity.Date
import com.tpp.theperiodpurse.data.findYears
import com.tpp.theperiodpurse.data.parseDatesIntoPeriods
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId

import java.util.*
import kotlin.collections.ArrayList

@Composable
fun PeriodHistoryLayout(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel,
    navController: NavController,
) {
    val dates = ArrayList(appViewModel.getDates())
    val bg = painterResource(R.drawable.colourwatercolour)
    val periods = parseDatesIntoPeriods(dates)
    var years = findYears(periods)
    if (years == null) {
        years = mutableMapOf()
        years[LocalDate.now().year] = ArrayList()
    }
    var yearSelected by remember { mutableStateOf(years.keys.last()) }
    // iterateing over years and create horizontally scrollable buttons
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
    { it ->
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
                LazyRow {
                    // reverse keys
                    val reversedKeys = years.keys.reversed()
                    for (key in reversedKeys) {
                        item {
                            YearTab(key,
                                yearSelected == key,
                                onClick = { yearSelected = key }
                            )
                        }
                    }
                }
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
                                text = yearSelected.toString(),
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
                        years[yearSelected]?.let { it1 -> PeriodEntries(it1, null) }
                    }
                }
            }
        }
    }
}

@Composable
fun PeriodEntries(periods: ArrayList<ArrayList<Date>>, upperbound: Int?) {
    if (periods.size == 0 || periods[0].size == 0) {
        Text(text = stringResource(R.string.please_start_logging_to_learn_more))
    } else {
        val length = if (upperbound != null) kotlin.math.min(periods.size, upperbound) else periods.size
        periods.reverse()
        Column {
            val formatter = SimpleDateFormat("MMM d", Locale.getDefault())
            // check if it's within a day, if so display current period...
            for (i in 0 until length) {
                val date = periods[i][0].date
                val converted = date?.toInstant()?.atZone(ZoneId.systemDefault())?.toLocalDate()?.year
                val current = LocalDate.now().year
                if (i == 0 && converted == current) {
                    val formattedDate = formatter.format(date)
                    // only if same year
                    Text(text = "Most Recent Period: Started $formattedDate")
                } else {
                    val period = periods[i]
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
}

@Composable
fun YearTab(year: Int, selected: Boolean = false, onClick: () -> Unit) {
    val red = Color(0xFFB31F20)
    val outlineColor = if (selected) red else Color.LightGray
    val contentColor = if (selected) Color.White else Color.LightGray
    val backgroundColor = if (selected) red else Color.White
    Button(
        onClick = onClick,
        border = BorderStroke(1.dp, outlineColor),
        shape = RoundedCornerShape(25),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = backgroundColor,
            contentColor = contentColor
        ),
        modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 10.dp)
    ){
        Text( text = year.toString() )
    }
}

@Preview
@Composable
fun YearTabFalsePreview() {
    YearTab(year = 2023, selected = false) {}
}

@Preview
@Composable
fun YearTabTruePreview() {
    YearTab(year = 2023, selected = true) {}
}