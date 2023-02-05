package com.example.theperiodpurse.ui.calendar

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class CalendarViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUIState(getDayInfo()))
    val uiState: StateFlow<CalendarUIState> = _uiState.asStateFlow()

    private fun getDayInfo(): LinkedHashMap<LocalDate, CalendarDayUIState> {
        return LinkedHashMap()
    }

    fun saveDayInfo(day: LocalDate, dayUIState: CalendarDayUIState){
        var newState = _uiState.value.days
        newState[day] = dayUIState
        _uiState.update { CalendarUIState(newState) }
    }
}

