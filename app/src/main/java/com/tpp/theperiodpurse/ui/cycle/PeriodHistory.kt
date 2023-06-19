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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.tpp.theperiodpurse.ui.viewmodel.AppViewModel
import com.tpp.theperiodpurse.R
import com.tpp.theperiodpurse.data.findYears
import com.tpp.theperiodpurse.data.parseDatesIntoPeriods
import com.tpp.theperiodpurse.ui.cycle.components.PeriodEntries
import com.tpp.theperiodpurse.ui.cycle.components.YearTab
import com.tpp.theperiodpurse.ui.onboarding.scaledSp
import java.time.LocalDate
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
    // Iterate over years and create horizontally scrollable buttons
    Scaffold(
        topBar = {
            TopAppBar (
                {
                    Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    text = "Period History",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.scaledSp()
                    )
                },
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
                LazyRow {
                    // Reverse keys
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
                    modifier.fillMaxWidth(),
                    elevation = 2.dp,
                    shape = RoundedCornerShape(10)
                ) {
                    Column(modifier.padding(horizontal = 15.dp, vertical = 15.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                // Replace the year
                                text = yearSelected.toString(),
                                fontSize = 15.scaledSp(),
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
                        // Show last three most recent periods
                        years[yearSelected]?.let { it1 -> PeriodEntries(it1, null) }
                    }
                }
            }
        }
    }
}