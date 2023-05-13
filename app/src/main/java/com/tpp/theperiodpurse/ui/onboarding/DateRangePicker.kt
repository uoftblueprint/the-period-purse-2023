package com.tpp.theperiodpurse.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.material.Icon
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.zIndex
import com.tpp.theperiodpurse.data.OnboardUIState
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateRangePicker(onSendButtonClicked: () -> Unit, viewModel: OnboardViewModel, uiState: OnboardUIState) {
    val calendar = Calendar.getInstance()
    val snackState = remember { SnackbarHostState() }
    val dayRange = uiState.days.toLong()
    SnackbarHost(hostState = snackState, Modifier.zIndex(1f),)
    val state = rememberDateRangePickerState(
        yearRange = IntRange(calendar.get(Calendar.YEAR)-3, calendar.get(Calendar.YEAR)),
        initialSelectedStartDateMillis = null,
        initialSelectedEndDateMillis = null,
        selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis < System.currentTimeMillis()
            }
        }
    )

    if (uiState.date != "" && uiState.date != "Choose Date" && state.selectedStartDateMillis == null){
        var dates= uiState.date.split(" to ")
        val date = convertDateToEpochMillis(dates[0])
        val endDate = convertDateToEpochMillis(dates[1])
        if (dayRange > 0) {
            state.setSelection(
                date,
                addDaysToSelectedStartDate(date, dayRange))
        } else {
            state.setSelection(
                date,
                endDate)
        }
    }

    Box(modifier = Modifier.background(Color.White)){
        Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
            // Add a row with "Save" and dismiss actions.
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 12.dp, end = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onSendButtonClicked() }) {
                    Icon(Icons.Filled.Close, contentDescription = "Localized description")
                }
                TextButton(
                    onClick = {
                        uiState.dateOptions = getDatesBetween(state.selectedStartDateMillis!!, state.selectedEndDateMillis!!)
                        viewModel.setDate(formatEpochMilliseconds(state.selectedStartDateMillis!!, state.selectedEndDateMillis!!))
                        onSendButtonClicked()
                    },
                    enabled = state.selectedEndDateMillis != null
                ) {
                    Text(text = "Done", color = Color.Black)
                }
            }
            if (state.selectedEndDateMillis != null && state.selectedEndDateMillis!! > System.currentTimeMillis()) {
                state.setSelection(state.selectedStartDateMillis, System.currentTimeMillis())
            }

            DateRangePicker(
                state = state,
                modifier = Modifier.weight(1f),
                colors = DatePickerDefaults.colors(
                    dayInSelectionRangeContainerColor = Color(97, 153, 154).copy(alpha = 0.3f),
                    selectedDayContainerColor = Color(97, 153, 154),
                    todayContentColor = Color.Black,
                    todayDateBorderColor = Color.Black,
                    dividerColor = Color(97, 153, 154)
                )
            )

            if (state.selectedStartDateMillis != null && dayRange > 0){
                state.setSelection(
                    state.selectedStartDateMillis,
                    addDaysToSelectedStartDate(state.selectedStartDateMillis!!, dayRange))
            }
        }
    }
}

fun addDaysToSelectedStartDate(selectedStartDateMillis: Long, days: Long): Long {
    val secondsToAdd = days * 24 * 60 * 60
    val instant = Instant.ofEpochMilli(selectedStartDateMillis)
    val newStartDate = instant.plusSeconds(secondsToAdd)
    return newStartDate.toEpochMilli()
}

fun formatEpochMilliseconds(startEpochMilliseconds: Long, endEpochMilliseconds: Long): String {
    val startinstant = Instant.ofEpochMilli(startEpochMilliseconds)
    val startlocalDate = ZonedDateTime.ofInstant(startinstant, ZoneOffset.UTC).toLocalDate()
    val endinstant = Instant.ofEpochMilli(endEpochMilliseconds)
    val endlocalDate = ZonedDateTime.ofInstant(endinstant, ZoneOffset.UTC).toLocalDate()
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    return startlocalDate.format(formatter) + "|" + endlocalDate.format(formatter)
}

fun convertDateToEpochMillis(dateString: String): Long {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val localDate = LocalDate.parse(dateString, formatter)
    val instant = localDate.atStartOfDay(ZoneOffset.UTC).toInstant()
    return instant.toEpochMilli()
}

fun getDatesBetween(startEpochMilliseconds: Long, endEpochMilliseconds: Long): List<Date> {
    val startDate = Instant.ofEpochMilli(startEpochMilliseconds)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()
    val endDate = Instant.ofEpochMilli(endEpochMilliseconds)
        .atZone(ZoneOffset.UTC)
        .toLocalDate()

    val dates = mutableListOf<Date>()
    var currentDate = startDate

    while (!currentDate.isAfter(endDate)) {
        val date = Date.from(currentDate.atStartOfDay(ZoneOffset.UTC).toInstant())
        dates.add(date)
        currentDate = currentDate.plusDays(1)
    }

    return dates
}
