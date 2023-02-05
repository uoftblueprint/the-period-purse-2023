package com.example.theperiodpurse.ui.onboarding

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import androidx.lifecycle.ViewModel
import com.example.theperiodpurse.data.OnboardUIState

class OnboardViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(OnboardUIState(dateOptions = dateOptions()))
    val uiState: StateFlow<OnboardUIState> = _uiState.asStateFlow()

    /**
     * Set the quantity [averageDays] for average period length for this onboarding session
     */
    fun setQuantity(numberDays: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                days = numberDays,

                )
        }
    }



    /**
     * Set the [logSymptoms] to track for onboarding session.
     */
    fun setSymptoms(logSymptoms: String) {
        var listOfSymptoms: MutableList<String> = mutableListOf()
        var tempList = logSymptoms.split("|")
        tempList.forEach { option ->
            if (option != "") {
                listOfSymptoms.add(option)
            }
        }

        _uiState.update { currentState ->
            currentState.copy(symptomsOptions = listOfSymptoms)
        }
    }

    /**
     * Set the [lastDate] of last period for current onboarding session
     */
    fun setDate(startDate: String) {
        var dates= startDate.split("|")
        _uiState.update { currentState ->
            currentState.copy(
                date = dates[0] + " to " + dates[1],
            )
        }
    }

    /**
     * Reset the onboard state
     */
    fun resetOrder() {
        _uiState.value = OnboardUIState(dateOptions = dateOptions())
    }


    /**
     * Returns a list of date options starting with the current date and the following 3 dates.
     */

    private fun dateOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // add current date and the following 30 previous dates.
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, -1)
        }
        dateOptions.reverse()
        return dateOptions
    }

}