package com.example.theperiodpurse.ui.calendar

import androidx.lifecycle.ViewModel
import com.example.theperiodpurse.data.Symptom
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalDate

class CalendarViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CalendarUIState(getDayInfo(), Symptom.FLOW))
    val uiState: StateFlow<CalendarUIState> = _uiState.asStateFlow()

    private fun getDayInfo(): LinkedHashMap<LocalDate, CalendarDayUIState> {
        return LinkedHashMap()
    }

    fun saveDayInfo(day: LocalDate, dayUIState: CalendarDayUIState){
        _uiState.value.days[day] = dayUIState
        _uiState.update { state -> CalendarUIState(state.days, state.selectedSymptom) }
    }

    fun setSelectedSymptom(symptom: Symptom) {
        _uiState.update { state -> CalendarUIState(state.days, symptom) }
    }
}

